package com.example.dhktpm15a_nhom06_todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AddScreenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_screen);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.android_dropdown_arrays,
                R.layout.color_spinner_layout
        );
        Spinner spinerAddScreen = findViewById(R.id.spinnerAdd);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinerAddScreen.setAdapter(adapter);
        spinerAddScreen.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}