package com.example.dhktpm15a_nhom06_todoapp.activity;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dhktpm15a_nhom06_todoapp.AddScreenActivity;
import com.example.dhktpm15a_nhom06_todoapp.MainActivity;
import com.example.dhktpm15a_nhom06_todoapp.R;
import com.example.dhktpm15a_nhom06_todoapp.adaper.TaskAdapter;
import com.example.dhktpm15a_nhom06_todoapp.dao.TaskDaoFireBase;
//import com.example.dhktpm15a_nhom06_todoapp.database.AppDatabase;
import com.example.dhktpm15a_nhom06_todoapp.model.Task;
import com.example.dhktpm15a_nhom06_todoapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<Task> listTask;
    private ListView listView;
    private List<Task> listTaskFilter;
    private TextView txtNameUser;
    private ImageView imgLogout;
    private Button btnAddTask;
    private  Task task;
    private TaskDaoFireBase taskDaoFireBase;
    private   TaskAdapter taskAdapter;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_DELETE = 444;
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    private int positionSelect = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().hide();


        taskDaoFireBase = new TaskDaoFireBase();
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


        //get all from realtime DB
        renderListTaskFromRealtimeDatabase();


        listView = (ListView) findViewById(R.id.idListView);
        TaskAdapter taskAdapter = new TaskAdapter(this, R.layout.activity_item_task, listTask);
        Log.d(TAG, String.valueOf(listTask.size()));
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                task = listTask.get(i);
                Log.d(TAG, task.getTitle());
            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            String displayName = user.getDisplayName();
            Log.d(TAG, "displayname: "+displayName);

            for (UserInfo userInfo : user.getProviderData()) {
                if (displayName == null && userInfo.getDisplayName() != null) {
                    displayName = userInfo.getDisplayName();
                }
            }
            txtNameUser.setText("Hi Admin");
//            if (user.getDisplayName().length() == 0) {
//                txtNameUser.setText("Hi, admin ");
//            } else {
//                txtNameUser.setText("Hi, " + user.getDisplayName());
//            }
        }

//
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = db.getReference("User");
//
//        Intent intent = getIntent();
//        if (intent != null) {
//            String uid = intent.getStringExtra("uid");
//            myRef.child(uid).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    User user1 = snapshot.getValue(User.class);
//                    Log.d(TAG, user1.toString());
//                    txtNameUser.setText("Hi " + user1.getName());
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//        }


            //log out
            imgLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {// Toast.makeText(MainActivity.this, "user registered sucessfilly", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(HomeActivity.this)
                            .setMessage("Are you sure want to logout ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mAuth.signOut();
                                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("No",null).show();


                }
            });
        }
        


    //render list from realtime firebase
    private void renderListTaskFromRealtimeDatabase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference = db.getReference(user.getEmail().substring(0, user.getEmail().length() - 4));


        listTask = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Task task = dataSnapshot.getValue(Task.class);

                    listTask.add(task);


                }

                //sort list by date
                Collections.sort(listTask, new Comparator<Task>() {
                    public int compare(Task o1, Task o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });

                listView = (ListView) findViewById(R.id.idListView);
                taskAdapter = new TaskAdapter(HomeActivity.this, R.layout.activity_item_task, listTask);
                listView.setAdapter(taskAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Get list Task faild : " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void FilterTask(String name){


        if (name.contains("All")){
            name="";
        }

        listTaskFilter = new ArrayList<>();
        for (Task task : listTask){
            if (task.getTitle().contains(name)){
                listTaskFilter.add(task);
            }
        }

        TaskAdapter taskAdapter = new TaskAdapter(this,R.layout.activity_item_task,listTaskFilter);
        listView.setAdapter(taskAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        FilterTask(adapterView.getSelectedItem().toString());
       Log.d("TitleTask:",adapterView.getSelectedItem().toString());
       if (adapterView.getSelectedItem().toString().contains("All")){
           listTaskFilter =listTask;
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select Action");
        menu.add(0, MENU_ITEM_EDIT, 0, "Update Task");
        menu.add(0, MENU_ITEM_DELETE, 0, "Delete Task");
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
        int position = info.position;
        task= listTaskFilter.get(position);
        Log.d(TAG,task.getTitle());
        Log.d(TAG, String.valueOf(position));


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==MENU_ITEM_EDIT){
            AlertDialog.Builder mBuilder= new AlertDialog.Builder(HomeActivity.this);
            View  view= getLayoutInflater().inflate(R.layout.update_task,null);
            EditText edContent = view.findViewById(R.id.idTextInput);
            Button btnUP = view.findViewById(R.id.idbntUp);
            edContent.setText(task.getContent());
            edContent.setTextColor(Color.BLACK);
            edContent.setSelectAllOnFocus(true);
            mBuilder.setView(view);
            AlertDialog dialog =   mBuilder.create();
            btnUP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String content = edContent.getText().toString().trim();
                    if(TextUtils.isEmpty(content)){
                        edContent.setError("text cannot br empty");
                        edContent.requestFocus();
                    }
                    else{
                        task.setContent(content);
                        taskDaoFireBase.updateTask(task).addOnSuccessListener(succ ->{
                            Toast.makeText(getApplication(), "Update task sucessfully!!", Toast.LENGTH_SHORT).show();
                            renderListTaskFromRealtimeDatabase();
                            dialog.cancel();
                        });
                    }

            }});


            dialog.show();
        }else if (item.getItemId()==MENU_ITEM_DELETE){
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure want to delete Task  ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                taskDaoFireBase.deleteTask(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                        Toast.makeText(getApplication(),"Delete sucessfully",Toast.LENGTH_LONG).show();
                                        renderListTaskFromRealtimeDatabase();
                                       // Log.d("Remove",task.getResult().toString());
                                    }
                                });
                        }
                    }).setNegativeButton("No",null).show();

        }



       // return super.onContextItemSelected(item);
        return true;


    }


}