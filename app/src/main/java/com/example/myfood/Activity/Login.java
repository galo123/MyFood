//new version
package com.example.myfood.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfood.Class.User;
import com.example.myfood.Fragment.FoodStock;
import com.example.myfood.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Login extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10 ; //can be any number
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
    private TextView creatAccountTV;
    private Button loginBtn;
    private Context context;

    //EditText txtName, txtAge, txtMail;
    // EditText txtPassword, txtFamilyname, txtTeamId;
    Button btnSave;
    DatabaseReference reff;
    User user;
   // LoginTable loginTable;

//auth
    private FirebaseAuth mAuth;
    List<AuthUI.IdpConfig> providers;
/*
    private String email, password; //why its gray?
*/
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
      //  updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        //needs to be implemented?
        boolean isSignedIn = (currentUser != null);

        if (isSignedIn) {

            Toast.makeText(context, "נרשמת בהצלחה", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ManageFood.class);
            startActivity(intent);
           finish();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //auth
        providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
        showSignInOptions();

        mAuth = FirebaseAuth.getInstance();

        // firebase connection to insert data
        reff = FirebaseDatabase.getInstance().getReference("User");

        context = this;
        teamET = findViewById(R.id.teamET);
        teamTV = findViewById(R.id.teamTV);
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
                        if (!loginUiCheck()) {
                            Toast.makeText(context, "האיימל או הסיסמא שגויים", Toast.LENGTH_LONG).show();

                        }

                        //firebase code
                      //  signIn(emailET.getText().toString(), passwordlET.getText().toString());


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

                            //firebase data insertion
                            user = new User(emailET.getText().toString().trim(), firstNameET.getText().toString().trim(), lastNameET.getText().toString().trim(), passwordlET.getText().toString().trim(),
                                    birthDayET.getText().toString().trim()
                            );


                            reff.push().setValue(user);

                            /*  int age=Integer.parseInt(birthDayET.getText().toString().trim());
                            User.setAge(age);
                            loginTable.setName(firstNameET.getText().toString().trim());
                            loginTable.setMail(emailET.getText().toString().trim());

                           */
                            Toast.makeText(Login.this, "data inserted successfully!",Toast.LENGTH_LONG ).show();

                            loginBtn.setText("הירשם");
                        }
                        break;
                    case "הירשם":
                        if(!groupUiCheck()){
                            Toast.makeText(context, "נרשמת בהצלחה", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, ManageFood.class);
                            startActivity(intent);
                            finish();
                        }

                        break;

                }

            }
        });
    }




    public boolean loginUiCheck() {
        boolean flag = false;
      //  getCurrentUser();
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

        if (teamET.getText().toString().equals("")) {
            teamET.setError("הכנס קוד קבוצה");
            flag = true;
        }

        return flag;

    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), MY_REQUEST_CODE);

    }


    private  void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                          //  updateUI(user);

                            Toast.makeText(context, "נרשמת בהצלחה", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, ManageFood.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                         /*   Log.w("tag", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            */

                        }

                        // ...
                    }
                });
    }

private void signIn(String email, String password) {
    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("tag", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    //   updateUI(user);

                        Toast.makeText(context, "נרשמת בהצלחה", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ManageFood.class);
                        startActivity(intent);
                        finish();

                    } /*else {
                        // If sign in fails, display a message to the user.
                        Log.w("tag", "signInWithEmail:failure", task.getException());
                        Toast.makeText(Login.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                    */

                    // ...
                }
            });
    }

    private void getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }

}
