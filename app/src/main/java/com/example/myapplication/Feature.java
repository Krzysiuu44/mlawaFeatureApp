package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "features")
public class Feature {
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    public Feature(JsonObject geometry, JsonObject properties, String type) {
        this.geometry = geometry;
        this.properties = properties;
        this.type = type;
    }

    @SerializedName("geometry")
    @ColumnInfo(name = "geometry")
    @TypeConverters(JsonObjectConverter.class)
    public JsonObject geometry;

    @ColumnInfo(name = "properties")
    @SerializedName("properties")
    @TypeConverters(JsonObjectConverter.class)
    public JsonObject properties;

    @SerializedName("type")
    public String type = "Feature";

    public JsonObject getGeometry() {
        return geometry;
    }

    public void setGeometry(JsonObject geometry) {
        this.geometry = geometry;
    }

    public JsonObject getProperties() {
        return properties;
    }

    public void setProperties(JsonObject properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }
}
