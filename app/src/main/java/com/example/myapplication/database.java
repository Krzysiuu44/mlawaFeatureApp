package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Feature.class},version = 1, exportSchema = false)
public abstract class database extends RoomDatabase {
    public abstract myDao myDao();
}
