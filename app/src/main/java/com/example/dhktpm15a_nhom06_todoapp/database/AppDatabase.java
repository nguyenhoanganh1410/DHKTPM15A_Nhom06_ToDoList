package com.example.dhktpm15a_nhom06_todoapp.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dhktpm15a_nhom06_todoapp.dao.TaskDao;
import com.example.dhktpm15a_nhom06_todoapp.model.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "task.db";
    private static AppDatabase instance;

    public  static  synchronized AppDatabase getInstance(Context contex){
        if(instance == null){
            instance = Room.databaseBuilder(contex.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries().build();

        }

        return instance;
    }

    public abstract TaskDao taskDao();
}