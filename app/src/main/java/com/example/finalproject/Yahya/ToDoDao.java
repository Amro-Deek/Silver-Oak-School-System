package com.example.finalproject.Yahya;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ToDoDao {
    @Insert
    void insert(ToDoRecord task);

    @Update
    void update(ToDoRecord task);

    @Delete
    void delete(ToDoRecord task);

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    List<ToDoRecord> getAllTasks();



}
