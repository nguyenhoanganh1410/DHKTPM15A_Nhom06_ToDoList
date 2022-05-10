package com.example.dhktpm15a_nhom06_todoapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {
    private TextView txtRegister;
    FirebaseAuth mAuth;
    private EditText etGmail;
    private EditText etPass;
    private Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtRegister = findViewById(R.id.txtRegister);
        btnSignIn = findViewById(R.id.buttonSignIn);
        etGmail = findViewById(R.id.editTextTextPersonName );
        etPass = findViewById(R.id.editTextTextPersonName2);


        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(LoginActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        //sign in with email/pass
        mAuth = FirebaseAuth.getInstance();
        btnSignIn.setOnClickListener(v ->{
            loggin();
        });

    }
    //sign in with google/pass
    private void loggin() {
        String email = etGmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            etGmail.setError("Email cannot br empty");
            etGmail.requestFocus();
        }else if(TextUtils.isEmpty(pass)){
            etPass.setError("Password cannot br empty");
            etPass.requestFocus();
        }else {
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful() ){
                        Toast.makeText(LoginActivity.this, "user signin sucessfilly", Toast.LENGTH_SHORT).show();
                       String uid = mAuth.getCurrentUser().getUid();
                        Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                        i.putExtra("uid",uid);
                        startActivity(i);

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "registration erro :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}