package com.example.dhktpm15a_nhom06_todoapp.databaseSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.example.dhktpm15a_nhom06_todoapp.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TodoList";

    public static class DatabaseColumn implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String  COLUMN_NAME_ID= "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATE = "date";
    }
    public DatabaseHandler(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DatabaseColumn.TABLE_NAME+ "("
                + DatabaseColumn.COLUMN_NAME_ID+ " INTEGER PRIMARY KEY," + DatabaseColumn.COLUMN_NAME_TITLE + " TEXT,"
                + DatabaseColumn.COLUMN_NAME_CONTENT + " TEXT," + DatabaseColumn.COLUMN_NAME_DATE+  "TEXT"+ ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseColumn.TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }
    void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseColumn.COLUMN_NAME_TITLE, task.getTitle()); // task Name
        values.put(DatabaseColumn.COLUMN_NAME_CONTENT, task.getContent());
        values.put(DatabaseColumn.COLUMN_NAME_DATE, task.getDate().toString()); //  date save “YYYY-MM-DD HH: MM: SS.SSS

        // Inserting Row
        db.insert(DatabaseColumn.TABLE_NAME, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    // get task by id
   Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DatabaseColumn.TABLE_NAME, new String[] { DatabaseColumn.COLUMN_NAME_ID,
                        DatabaseColumn.COLUMN_NAME_TITLE, DatabaseColumn.COLUMN_NAME_CONTENT,DatabaseColumn.COLUMN_NAME_DATE }
                , DatabaseColumn.COLUMN_NAME_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

       Task task = null;
       try {
           task = new Task(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                   SimpleDateFormat.getInstance().parse( cursor.getString(3)));
       } catch (ParseException e) {
           e.printStackTrace();
       }

       // return contact
        return task;
    }

    public List<Task> getAllTasks()  {
        List<Task> listTask = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " +  DatabaseColumn.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = null;
                try {
                    task = new Task(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                            SimpleDateFormat.getInstance().parse( cursor.getString(3)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // Adding task to list
                listTask.add(task);
            } while (cursor.moveToNext());
        }

        // return contact list
        return listTask;
    }
    // code to update the single task
    public int updateTask( Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseColumn.COLUMN_NAME_TITLE, task.getTitle()); // task Name
        values.put(DatabaseColumn.COLUMN_NAME_CONTENT, task.getContent());
        values.put(DatabaseColumn.COLUMN_NAME_DATE, task.getDate().toString()); //

        // updating row
        return db.update(DatabaseColumn.TABLE_NAME, values, DatabaseColumn.COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
    }

    // Deleting single task
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseColumn.TABLE_NAME, DatabaseColumn.COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(task.getId()) });
        db.close();
    }




}
