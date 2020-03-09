package com.example.myapplication;

import androidx.room.Entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

 //   @SerializedName("features")
    public List<Feature> features;


    public static Model parse(JsonElement element) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(element, Model.class);
    }

    public JsonObject toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJsonTree(this).getAsJsonObject();
    }
}
