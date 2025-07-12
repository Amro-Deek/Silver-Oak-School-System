package com.example.finalproject.Yahya;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ToDoRecord.class}, version = 1)
public abstract class ToDoDatabase extends RoomDatabase {
    public abstract ToDoDao studyTaskDao();

    private static volatile ToDoDatabase INSTANCE;

    public static ToDoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ToDoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ToDoDatabase.class, "study_planner_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
