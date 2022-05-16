package com.example.dhktpm15a_nhom06_todoapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhktpm15a_nhom06_todoapp.R;
import com.example.dhktpm15a_nhom06_todoapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        btnRegister = findViewById(R.id.btnRegister);
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
         else if(pass.length() < 6){
             etPass.setError("Password must be greater than or equal to 6");
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
                         if(!isConnected(RegisterActivity.this)){
                             showCustomeDialog();
                         }
                         else{
                             Toast.makeText(RegisterActivity.this, "registration erro :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }

                     }
                 }
             });
        }}


    public boolean isConnected(RegisterActivity login){
        ConnectivityManager connectivityManager = (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( (wifiConn != null && wifiConn.isConnected() )  || (mobileConn != null && mobileConn.isConnected()) ){
            return true;
        }

        return false;
    }

    public void showCustomeDialog () {
        AlertDialog.Builder mBuilder= new AlertDialog.Builder(RegisterActivity.this);
        mBuilder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }).setNegativeButton("Cancel",null).show();
    }
}