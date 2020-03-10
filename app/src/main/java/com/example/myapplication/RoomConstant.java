package com.example.myapplication;

import android.content.Context;

import androidx.room.Room;

public class RoomConstant {
    public final static database buildRoomDb(Context context) {
        return Room.databaseBuilder(context, database.class, "app_db") // tu ma wejsc nazwa bazy z serweru
                .fallbackToDestructiveMigration()
                //.allowMainThreadQueries()
                .build();
    }
}