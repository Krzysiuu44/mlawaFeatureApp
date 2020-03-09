package com.example.myapplication;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonObjectConverter {

    @TypeConverter
    public JsonObject stringToJson(String value) {
        return new Gson().fromJson(value, JsonObject.class);
    }

    @TypeConverter
    public String jsonToString(JsonObject value) {
        return value.toString();
    }
}
