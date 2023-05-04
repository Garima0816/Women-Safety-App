package com.Garima.NIDARR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText firstName,middleName,lastname,email,phoneno,password,confirmPassword;

    TextView login;

    Button register;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstName = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phoneno = findViewById(R.id.phoneno);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        register = findViewById(R.id.register);
        mAuth= FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
                    password.requestFocus();
                    password.setError("Passwords Don't Match !!");
                    password.setText("");
                    confirmPassword.setText("");
                }
                if (firstName.length() == 0 || lastname.length() == 0 || password.length() == 0 || confirmPassword.length() == 0 || email.length() == 0) {
                    Toast.makeText(Signup.this, "Please Fill Out All the Fields", Toast.LENGTH_SHORT).show();
                } else {
                    String firstname = firstName.getText().toString();
                    String lastName = lastname.getText().toString();
                    String emailID = email.getText().toString();
                    String phoneNo = phoneno.getText().toString();
                    String passwd = password.getText().toString();

                    mAuth.createUserWithEmailAndPassword(emailID,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Signup.this, "Account Created !!", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                String Uid = user.getUid();

                                rootNode = FirebaseDatabase.getInstance("https://nidarr-78f26-default-rtdb.firebaseio.com/");
                                reference = rootNode.getReference("Users");
                                Database helperClass = new Database(firstname, lastName, emailID, phoneNo, passwd);
                                reference.child(Uid).setValue(helperClass);
                                Intent i = new Intent(Signup.this, MainActivity.class);
                                //i.putExtra("name",Username);
                                startActivity(i);
                                finish();
                            }
                        }
                    });

                }
            }
        });

        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerPage = new Intent(Signup.this,Login.class);
                startActivity(registerPage);
            }
        });


    }
}