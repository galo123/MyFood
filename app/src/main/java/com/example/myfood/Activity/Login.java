//new version
package com.example.myfood.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfood.Class.Group;
import com.example.myfood.Class.User;
import com.example.myfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private EditText emailET;
    private EditText passwordlET;
    private EditText firstNameET;
    private EditText lastNameET;
    public static EditText birthDayET;
    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView birthDayTV;
    private TextView emailTV;
    private TextView passwordlTV;
    private TextView teamTV;
    private EditText teamET;
    private TextView newTeamTV;
    private EditText newTeamET;
    private TextView creatAccountTV;
    private Button loginBtn;
    private Context context;
    public static final String LOGIN_USER_KEY = "login_user_key";
    public static final String LOGIN_GROUP_KEY = "login_group_key";
    public User user;
    DatabaseReference reff_user;
    DatabaseReference reff_group;
    DatabaseReference reff_group_exists_user;
    public Group group;
    private FirebaseAuth mAuth;


    private void updateUI(FirebaseUser currentUser) {
        //needs to be implemented?
        boolean isSignedIn = (currentUser != null);

        if (isSignedIn) {

            Toast.makeText(context, "נרשמת בהצלחה", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ManageFood.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Login.LOGIN_USER_KEY, user);
            bundle.putSerializable(Login.LOGIN_GROUP_KEY, group);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //auth
        mAuth = FirebaseAuth.getInstance();

        context = this;
        teamET = findViewById(R.id.teamET);
        teamTV = findViewById(R.id.teamTV);
        //continue
        newTeamET = findViewById(R.id.newTeamET);
        newTeamTV = findViewById(R.id.newTeamTV);
        emailTV = findViewById(R.id.emailTV);
        emailET = findViewById(R.id.email);
        passwordlTV = findViewById(R.id.passwordTV);
        passwordlET = findViewById(R.id.password);
        firstNameET = findViewById(R.id.firstName);
        lastNameET = findViewById(R.id.lastName);
        birthDayET = findViewById(R.id.birthDayET);
        firstNameTV = findViewById(R.id.firstNameTV);
        lastNameTV = findViewById(R.id.lastNameTV);
        birthDayTV = findViewById(R.id.birthDayTV);
        creatAccountTV = findViewById(R.id.creatAccountTV);
        loginBtn = findViewById(R.id.login);

        firstNameET.setVisibility(View.GONE);
        firstNameTV.setVisibility(View.GONE);

        lastNameET.setVisibility(View.GONE);
        lastNameTV.setVisibility(View.GONE);

        birthDayET.setVisibility(View.GONE);
        birthDayTV.setVisibility(View.GONE);

        teamTV.setVisibility(View.GONE);
        teamET.setVisibility(View.GONE);
        newTeamTV.setVisibility(View.GONE);
        newTeamET.setVisibility(View.GONE);


        creatAccountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (creatAccountTV.getText().toString()) {
                    case "עדיין אין לך חשבון? תלחץ כאן!":
                        firstNameET.setVisibility(View.VISIBLE);
                        firstNameTV.setVisibility(View.VISIBLE);

                        lastNameET.setVisibility(View.VISIBLE);
                        lastNameTV.setVisibility(View.VISIBLE);

                        birthDayET.setVisibility(View.VISIBLE);
                        birthDayTV.setVisibility(View.VISIBLE);

                        loginBtn.setText("הבא");

                        creatAccountTV.setText("חזור");
                        break;
                    case "חזור":
                        firstNameET.setVisibility(View.GONE);
                        firstNameTV.setVisibility(View.GONE);

                        lastNameET.setVisibility(View.GONE);
                        lastNameTV.setVisibility(View.GONE);

                        birthDayET.setVisibility(View.GONE);
                        birthDayTV.setVisibility(View.GONE);

                        loginBtn.setText("התחבר");
                        creatAccountTV.setText("עדיין אין לך חשבון? תלחץ כאן!");
                        break;
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (loginBtn.getText().toString()) {
                    case "התחבר":
                        if (loginUiCheck()) {

                              Toast.makeText(context, "האיימל או הסיסמא שגויים", Toast.LENGTH_LONG).show();

                        }

                        else {

                            reff_group_exists_user = FirebaseDatabase.getInstance().getReference("Groups");

                            reff_group_exists_user.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                                        Group retrievedGroup = keyNode.child("").getValue(Group.class); //cast to group

                                        for(User member: retrievedGroup.getFamilyMembers()){
                                            if (member.getEmail().equals(emailET.getText().toString().trim())) {
                                                user = member;
                                                group = retrievedGroup;
                                            }
                                            mAuth.signInWithEmailAndPassword(emailET.getText().toString().trim(), passwordlET.getText().toString().trim()).
                                                    addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                if(user != null && group != null) {
                                                                    // Sign in success, update UI with the signed-in user's information
                                                                    Log.d("TAG_success", "signInWithEmail:success");
                                                                   // Toast.makeText(context, "ברוך הבא!", Toast.LENGTH_LONG).show();
                                                                    Intent intent = new Intent(context, ManageFood.class);
                                                                    Bundle bundle = new Bundle();
                                                                    bundle.putSerializable(Login.LOGIN_USER_KEY, user);
                                                                    bundle.putSerializable(Login.LOGIN_GROUP_KEY, group);
                                                                    intent.putExtras(bundle);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }

                                                            } else {
                                                                // If sign in fails, display a message to the user.
                                                                Log.w("TAG_fail", "signInWithEmail:failure", task.getException());
                                                                Toast.makeText(context, "Authentication failed.",
                                                                        Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    });
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Log.w("TAG", "Failed to read value.", databaseError.toException());
                                }
                            });
                        }
                        break;
                    case "הבא":
                        if (!signUiCheck()) {
                            emailET.setVisibility(View.GONE);
                            emailTV.setVisibility(View.GONE);
                            passwordlTV.setVisibility(View.GONE);
                            passwordlET.setVisibility(View.GONE);
                            firstNameET.setVisibility(View.GONE);
                            lastNameET.setVisibility(View.GONE);
                            birthDayET.setVisibility(View.GONE);
                            firstNameTV.setVisibility(View.GONE);
                            lastNameTV.setVisibility(View.GONE);
                            birthDayTV.setVisibility(View.GONE);
                            creatAccountTV.setVisibility(View.GONE);

                            teamET.setVisibility(View.VISIBLE);
                            teamTV.setVisibility(View.VISIBLE);
                            newTeamET.setVisibility(View.VISIBLE);
                            newTeamTV.setVisibility(View.VISIBLE);

                            //firebase data insertion
                            user = new User();
                            user.setEmail(emailET.getText().toString().trim());
                            user.setBirthDay(birthDayET.getText().toString().trim());
                            user.setFirstName(firstNameET.getText().toString().trim());
                            user.setLastName(lastNameET.getText().toString().trim());
                            user.setPassword(passwordlET.getText().toString().trim());
                            loginBtn.setText("הירשם");
                        }
                        break;
                    case "הירשם":
                        if (!groupUiCheck()) {
                            //אם המשתמש מילא קבוצה

                            Toast.makeText(context, "נרשמת בהצלחה", Toast.LENGTH_SHORT).show();

                            mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).
                                    addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Log.d("tag", "createUserWithEmail:success");
                                                Intent intent = new Intent(context, ManageFood.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable(Login.LOGIN_USER_KEY, user);
                                                bundle.putSerializable(Login.LOGIN_GROUP_KEY, group);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                                Toast.makeText(context, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        break;
                }

            }
        });
    }

    public boolean loginUiCheck() {
        boolean flag = false;
        if (emailET.getText().toString().equals("")) {
            emailET.setError("הכנס איימל");
            flag = true;
        }
        if (passwordlET.getText().toString().equals("")) {
            passwordlET.setError("הכנס סיסמא");
            flag = true;
        }

        return flag;

    }

    public boolean signUiCheck() {
        boolean flag = false;

        if (emailET.getText().toString().equals("")) {
            emailET.setError("הכנס איימל");
            flag = true;
        }
        if (firstNameET.getText().toString().equals("")) {
            firstNameET.setError("הכנס שם פרטי");
            flag = true;
        }
        if (lastNameET.getText().toString().equals("")) {
            lastNameET.setError("הכנס שם משפחה");
            flag = true;
        }
        if (birthDayET.getText().toString().equals("")) {
            birthDayET.setError("הכנס גיל");
            flag = true;
        }
        return flag;

    }

    public boolean groupUiCheck() {
        boolean flag = false;

        //both fields are  null
        if (teamET.getText().toString().equals("") && newTeamET.getText().toString().equals("")) {
            teamET.setError("הכנס קוד קבוצה קיים או שם קבוצה חדש");
            flag = true;
        }
        //both fields are not null
        else if ((!teamET.getText().toString().equals("")) && !(newTeamET.getText().toString().equals(""))) {
            Toast.makeText(context, "אנא הכנס נתונים רק באחד מהשדות - קבוצה חדשה או קיימת", Toast.LENGTH_SHORT).show();
            flag = true;
        }

        //check if old group is valid
        else if (!teamET.getText().toString().equals("")) {
            //update user group code with the ET group code
            reff_group = FirebaseDatabase.getInstance().getReference("Groups");

            // Read from the database
            reff_group.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean flagQuery=false;
                    for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                        Group retrievedGroup = keyNode.child("").getValue(Group.class); //cast to group

                        if (retrievedGroup != null && teamET.getText().toString().trim().
                                equals(retrievedGroup.getGroupName())) {
                            user.setGroupCode(retrievedGroup.getGroupCode()); //update user's group code
                            retrievedGroup.addFamilyMemberToGroup(user);
                            group = retrievedGroup;


                            //push retrievedGroup back to DB
                           reff_group = FirebaseDatabase.getInstance().getReference("Groups").child(teamET.getText().toString());//.child(teamET.getText().toString().trim());//.child("familyMembers");
                           reff_group.setValue(retrievedGroup);

                              flagQuery = true;
                           // Toast.makeText(context, "נרשמת בהצלחה", Toast.LENGTH_SHORT).show();

                            mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).
                                    addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Log.d("tag", "createUserWithEmail:success");
                                                Intent intent = new Intent(context, ManageFood.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable(Login.LOGIN_USER_KEY, user);
                                                bundle.putSerializable(Login.LOGIN_GROUP_KEY,group);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                                finish();

                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                                Toast.makeText(context, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });



                          //  break;
                        }
                    }




                }
                    @Override
                    public void onCancelled (DatabaseError error){
                        // Failed to read value
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }



            });

            flag = true;

        }

        else if (!newTeamET.getText().toString().equals("")) {
            //creating new group with the entered name
            group = new Group(newTeamET.getText().toString().trim());

            //update user group code with the new group code and add it to groupMembers
           user.setGroupCode(group.getGroupCode());
           // reff_user = FirebaseDatabase.getInstance().getReference("Groups").child(newTeamET.getText().toString()).child("Users");
           // reff_user.push().setValue(user);
           // Toast.makeText(Login.this, "users data inserted successfully!", Toast.LENGTH_LONG).show();

            group.addFamilyMemberToGroup(user);
            reff_group = FirebaseDatabase.getInstance().getReference("Groups").child(newTeamET.getText().toString());
            reff_group.setValue(group);
            mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).
                    addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("tag", "createUserWithEmail:success");
                                Intent intent = new Intent(context, ManageFood.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Login.LOGIN_USER_KEY, user);
                                bundle.putSerializable(Login.LOGIN_GROUP_KEY,group);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        return flag;
    }

}
