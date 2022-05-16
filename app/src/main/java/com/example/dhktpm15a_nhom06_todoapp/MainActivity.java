package com.example.dhktpm15a_nhom06_todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dhktpm15a_nhom06_todoapp.activity.HomeActivity;
import com.example.dhktpm15a_nhom06_todoapp.activity.LoginActivity;
import com.example.dhktpm15a_nhom06_todoapp.activity.RegisterActivity;
import com.example.dhktpm15a_nhom06_todoapp.utils.NetWorkUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button btnHomeSignIn;
    private Button btnHomeRegister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        btnHomeSignIn = findViewById(R.id.btnHomeSignIn);
        btnHomeRegister = findViewById(R.id.btnHomeRegister);

        btnHomeSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MainActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnHomeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if(user == null ){
//            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//            startActivity(intent);
        }else{
            if(!NetWorkUtil.isConnected(MainActivity.this)){
                showCustomeDialog();
            }
            else{
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }

        }

    }

    public void showCustomeDialog () {
        AlertDialog.Builder mBuilder= new AlertDialog.Builder(MainActivity.this);
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