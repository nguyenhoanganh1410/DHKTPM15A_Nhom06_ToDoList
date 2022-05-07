package com.example.dhktpm15a_nhom06_todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dhktpm15a_nhom06_todoapp.activity.HomeActivity;
import com.example.dhktpm15a_nhom06_todoapp.activity.LoginActivity;
import com.example.dhktpm15a_nhom06_todoapp.activity.RegisterActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnHomeSignIn;
    private Button btnHomeRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHomeSignIn = findViewById(R.id.btnHomeSignIn);
        btnHomeRegister = findViewById(R.id.btnHomeRegister);

        btnHomeSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(MainActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
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
}