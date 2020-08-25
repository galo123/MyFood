package com.example.myfood.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfood.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class datainsertionActivity extends AppCompatActivity {
    EditText txtName, txtAge, txtMail;
   // EditText txtPassword, txtFamilyname, txtTeamId;
    Button btnSave;
    DatabaseReference reff;
    LoginTable loginTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName=(EditText)findViewById(R.id.firstName);
        txtAge=(EditText)findViewById(R.id.birthDayET);
        txtMail=(EditText)findViewById(R.id.email);
        btnSave = (Button)findViewById(R.id.login);

      /*  txtFamilyname=(EditText)findViewById(R.id.lastName);
        txtPassword=(EditText)findViewById(R.id.password);
        txtTeamId=(EditText)findViewById(R.id.teamET);
*/
        loginTable = new LoginTable();

        reff = FirebaseDatabase.getInstance().getReference("LoginTable");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int age=Integer.parseInt(txtAge.getText().toString().trim());
               loginTable.setAge(age);
               loginTable.setName(txtName.getText().toString().trim());
               loginTable.setMail(txtMail.getText().toString().trim());

               reff.push().setValue(loginTable);
                Toast.makeText(datainsertionActivity.this, "data inserted successfully!",Toast.LENGTH_LONG ).show();
            }
        });
    }
}
