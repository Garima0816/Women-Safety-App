package com.Garima.NIDARR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private Button register,login;

    private EditText email;
    private EditText password;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.length()==0){
                    if(password.length()==0){
                        email.requestFocus();
                        email.setError("Please Enter an Email");
                        email.requestFocus();
                        email.setError("Please Enter Password");
                    }
                    else {
                        email.requestFocus();
                        email.setError("Please Enter a Username");
                    }
                }
                else if (password.length()==0){
                    password.requestFocus();
                    password.setError("Please Enter Password");
                }
                else{
                    //String n= uname.getText().toString();
                    isUser();
                }
            }
        });

    }

    private void isUser() {

        String enteredUsername = email.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(enteredUsername, enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    startActivity(new Intent(Login.this, MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "Login Failed!!.Signup Your Account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
