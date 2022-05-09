package com.example.dhktpm15a_nhom06_todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dhktpm15a_nhom06_todoapp.activity.HomeActivity;
import com.example.dhktpm15a_nhom06_todoapp.activity.RegisterActivity;
import com.example.dhktpm15a_nhom06_todoapp.dao.TaskDaoFireBase;
import com.example.dhktpm15a_nhom06_todoapp.database.AppDatabase;
import com.example.dhktpm15a_nhom06_todoapp.model.Task;

import java.util.Date;

public class AddScreenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnAddTask;
    private EditText edContent;

    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_screen);
        initUI();

        //event add Task
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // addTask();
                // Toast.makeText(MainActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
                addTaskIntoRealtime();
            }
        });


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
        title = adapterView.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void initUI(){
        btnAddTask = findViewById(R.id.btnAddScreen);
        edContent = findViewById(R.id.txtAddContent);
    }

    public void addTask(){
        String content = edContent.getText().toString().trim();
        if(TextUtils.isEmpty(content)){
            edContent.setError("Email cannot br empty");
            edContent.requestFocus();
        }
        else if(title == ""){
            Toast.makeText(this, "Bạn chưa chọn loại công việc?", Toast.LENGTH_SHORT).show();
        }
        else{
            //them vao db
            long millis = System.currentTimeMillis();
            Task task = new Task(title,content, new Date(millis));
            AppDatabase.getInstance(this).taskDao().insertTask(task);
            Toast.makeText(this, "Add task sucessfully!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddScreenActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    public void addTaskIntoRealtime(){
        TaskDaoFireBase taskDaoFireBase = new TaskDaoFireBase();
        String content = edContent.getText().toString().trim();
        if(TextUtils.isEmpty(content)){
            edContent.setError("Email cannot br empty");
            edContent.requestFocus();
        }
        else if(title == ""){
            Toast.makeText(this, "Bạn chưa chọn loại công việc?", Toast.LENGTH_SHORT).show();
        }
        else{
            //them vao db
            long millis = System.currentTimeMillis();
            Task task = new Task(radomID(),title,content, new Date(millis));

            taskDaoFireBase.addTask(task).addOnSuccessListener(succ ->{
                Toast.makeText(this, "Add task sucessfully!!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(f ->{
                Toast.makeText(this, "Add task failed" +
                        "!!", Toast.LENGTH_SHORT).show();
            });



            Intent intent = new Intent(AddScreenActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    //radom id
    public int radomID(){
        int min = 0;
        int max = 400;

        //Generate random int value from 200 to 400
       // System.out.println("Random value of type int between "+min+" to "+max+ ":");
        int b = (int)(Math.random()*(max-min+1)+min);

        return b;
    }
}