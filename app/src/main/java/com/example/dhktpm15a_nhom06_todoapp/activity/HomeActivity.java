package com.example.dhktpm15a_nhom06_todoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dhktpm15a_nhom06_todoapp.R;
import com.example.dhktpm15a_nhom06_todoapp.adaper.TaskAdapter;
import com.example.dhktpm15a_nhom06_todoapp.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<Task> listTask;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        //custom drop list
        Spinner spiner = findViewById(R.id.spinner2);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.android_dropdown_arrays,
                R.layout.color_spinner_layout
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spiner.setAdapter(adapter);
        spiner.setOnItemSelectedListener(this);
        //


        listView = (ListView) findViewById(R.id.idListView);
        listTask = new ArrayList<>();
        long millis = System.currentTimeMillis();
        listTask.add(new Task(1, "Work", "android", new java.util.Date(millis)      ));
        listTask.add(new Task(2, "Work", "android", new java.util.Date(millis)      ));
        listTask.add(new Task(3, "Work", "android", new java.util.Date(millis)      ));
        listTask.add(new Task(4, "Work", "android", new java.util.Date(millis)      ));





        TaskAdapter taskAdapter = new TaskAdapter(this, R.layout.activity_item_task, listTask);
        listView.setAdapter(taskAdapter);



    }


    //su kien chon 1 item trong drop list
    //viet them ....
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}