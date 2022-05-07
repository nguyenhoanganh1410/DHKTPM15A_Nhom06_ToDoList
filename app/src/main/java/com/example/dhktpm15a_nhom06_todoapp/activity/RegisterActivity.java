package com.example.dhktpm15a_nhom06_todoapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhktpm15a_nhom06_todoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TextView txtSignIn;
    private Button btnRegister;
    private EditText etGmail;
    private EditText etPass;
    private EditText etPassAgain;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtSignIn = (TextView) findViewById(R.id.txtSignin);
        btnRegister = findViewById(R.id.button4);
        etGmail = findViewById(R.id.emailRegister );
        etPass = findViewById(R.id.passworldRegister);
        etPassAgain = findViewById(R.id.passworldRegisterAgain);

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(RegisterActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createUser();
               // Toast.makeText(RegisterActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
            }
        });

        mAuth = FirebaseAuth.getInstance();


    }
    private void createUser() {
        String email = etGmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String passAgian = etPassAgain.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            etGmail.setError("Email cannot br empty");
            etGmail.requestFocus();
        }else if(TextUtils.isEmpty(pass)){
            etPass.setError("Password cannot br empty");
            etPass.requestFocus();

        }
        else if(!pass.equals(passAgian)){
            etPass.setError("Password not equal");
            etPassAgain.setError("Password not equal");
            etPass.requestFocus();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful() ){
                        Toast.makeText(RegisterActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "registration erro :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }}
}