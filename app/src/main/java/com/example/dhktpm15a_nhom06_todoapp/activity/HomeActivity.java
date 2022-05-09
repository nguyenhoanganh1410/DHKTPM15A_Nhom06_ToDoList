package com.example.dhktpm15a_nhom06_todoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dhktpm15a_nhom06_todoapp.AddScreenActivity;
import com.example.dhktpm15a_nhom06_todoapp.MainActivity;
import com.example.dhktpm15a_nhom06_todoapp.R;
import com.example.dhktpm15a_nhom06_todoapp.adaper.TaskAdapter;
import com.example.dhktpm15a_nhom06_todoapp.database.AppDatabase;
import com.example.dhktpm15a_nhom06_todoapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<Task> listTask;
    private ListView listView;

    private TextView txtNameUser;
    private ImageView imgLogout;
    private Button btnAddTask;
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //get id
        txtNameUser = findViewById(R.id.txtNameUser);
        imgLogout = findViewById(R.id.imgLogout);
        btnAddTask = findViewById(R.id.btnAddTask);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, AddScreenActivity.class);
                startActivity(intent);
            }
        });

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




        //listTask = AppDatabase.getInstance(this).taskDao().getAll();

        //get all from realtime DB
        renderListTaskFromRealtimeDatabase();
//        Toast.makeText(HomeActivity.this, String.valueOf(listTask.size()), Toast.LENGTH_SHORT).show();
//        long millis = System.currentTimeMillis();
//        listTask.add(new Task(1, "Work", "android", new Date(millis)      ));
//        listTask.add(new Task(2, "Work", "android", new Date(millis)      ));
//        listTask.add(new Task(3, "Work", "android", new Date(millis)      ));
//        listTask.add(new Task(4, "Work", "android", new Date(millis)      ));




//        listView = (ListView) findViewById(R.id.idListView);
//        TaskAdapter taskAdapter = new TaskAdapter(this, R.layout.activity_item_task, listTask);
//        listView.setAdapter(taskAdapter);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            if(user.getDisplayName().length() == 0){
                txtNameUser.setText("Hi, admin ");
            }
            else{
                txtNameUser.setText("Hi, "+ user.getDisplayName());
            }

        }
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    //su kien chon 1 item trong drop list
    //viet them ....
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       // Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //render list from realtime firebase
    private void renderListTaskFromRealtimeDatabase(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
       databaseReference = db.getReference(user.getEmail().substring(0, user.getEmail().length()-4));


        List<Task> list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Task task = dataSnapshot.getValue(Task.class);

                    list.add(task);


                }
                listView = (ListView) findViewById(R.id.idListView);
                TaskAdapter taskAdapter = new TaskAdapter(HomeActivity.this, R.layout.activity_item_task, list);
                listView.setAdapter(taskAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Get list Task faild : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}