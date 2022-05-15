package com.example.dhktpm15a_nhom06_todoapp.dao;

import com.example.dhktpm15a_nhom06_todoapp.model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaskDaoFireBase {
    private DatabaseReference databaseReference;

    public TaskDaoFireBase (){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference = db.getReference(user.getEmail().substring(0, user.getEmail().length()-4));
    }

    public com.google.android.gms.tasks.Task<Void> addTask(Task task){
        String pathObject = String.valueOf(task.getId());
        return databaseReference.child(pathObject).setValue(task);
    }
    public com.google.android.gms.tasks.Task<Void> updateTask(Task  task){
        String pathObject = String.valueOf(task.getId());
        return databaseReference.child(pathObject).setValue(task);

    }
    public com.google.android.gms.tasks.Task<Void> deleteTask(Task  task){
        String pathObject = String.valueOf(task.getId());
        return databaseReference.child(pathObject).removeValue();

    }
}
