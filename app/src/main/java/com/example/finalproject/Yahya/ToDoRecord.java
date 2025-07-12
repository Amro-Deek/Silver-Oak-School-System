package com.example.finalproject.Yahya;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class ToDoRecord {

    public ToDoRecord(String title, boolean isCompleted) {
        this.title = title;
        this.isCompleted = isCompleted;
    }


    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "is_completed")
    public boolean isCompleted;
}
