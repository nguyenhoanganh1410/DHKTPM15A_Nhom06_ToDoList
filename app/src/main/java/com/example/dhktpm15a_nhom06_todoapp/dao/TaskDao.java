package com.example.dhktpm15a_nhom06_todoapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dhktpm15a_nhom06_todoapp.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Insert
    void insertTask(Task task);

    @Delete
    void delete(Task user);
}