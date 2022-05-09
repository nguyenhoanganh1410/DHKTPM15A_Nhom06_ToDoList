package com.example.dhktpm15a_nhom06_todoapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dhktpm15a_nhom06_todoapp.DateConverter;

import java.util.Date;

@Entity(tableName = "task")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;

    @TypeConverters({DateConverter.class})
    private Date date;


    public Task(int id, String title, String content, Date date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Task(String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
