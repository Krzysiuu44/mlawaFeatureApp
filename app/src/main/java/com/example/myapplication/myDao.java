package com.example.myapplication;

import android.annotation.SuppressLint;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@Dao
public abstract class myDao {

    @Insert
    protected abstract Single<Long> addModel_(Feature feature);

    @Insert
    public abstract Single<List<Long>> addModel(List<Feature> features);


    @SuppressLint("CheckResult")
    public Completable addModel(Feature feature) {
        return Completable.create(emitter -> {
            addModel_(feature)
                    .subscribeOn(Schedulers.io())
                    .subscribe(id -> {
                        feature.id = id;
                        emitter.onComplete();
                    });
        })
                .subscribeOn(Schedulers.io());
    }

    @Update
    public abstract void updateModel(Feature feature);

    @Query("Select count(*) FROM features") //komenda sql do serwera
    public abstract Single<Long> count(); //zwroci liczbe jsonow

    @Query("Select * FROM features")
    public abstract Single<List<Feature>> getList(); //zwroci liste features'ow

    @Query("Select * FROM features WHERE id = :a")
    public abstract Maybe<Feature> getFeatureById(long a); // zwroci wybranego feature
}