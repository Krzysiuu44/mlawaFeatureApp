package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class myDao {

    @Insert
    protected abstract long addModel_(Feature feature);

    @Insert
    public abstract List<Long> addModel(List<Feature> features);


    public void addModel(Feature feature) {
        feature.id = addModel_(feature);
    }

    @Update
    public abstract void updateModel(Feature feature);

    @Query("Select count(*) FROM features") //komenda sql do serwera
    public abstract long count(); //zwroci liczbe jsonow

    @Query("Select * FROM features")
    public abstract List<Feature> getList(); //zwroci liste features'ow

    @Query("Select * FROM features WHERE id = :a")
    public abstract Feature getFeatureById(long a); // zwroci wybranego feature
}
