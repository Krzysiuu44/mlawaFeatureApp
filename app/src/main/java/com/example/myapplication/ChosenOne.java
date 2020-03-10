package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChosenOne extends Fragment {

    private long wybraneId;
    private Feature feature;
    public TextView idek;
    public TextView geometry;
    public TextView properties;
    public TextView type;
    public String temp;
    public Object aaa;

    public ChosenOne(long id) {
        wybraneId = id;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public String toPrettyFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chosen_one, container, false);
        type = view.findViewById(R.id.showUpType);
        idek = view.findViewById(R.id.showUpId);
        geometry = view.findViewById(R.id.showUpGeometry);
        properties = view.findViewById(R.id.showUpProperties);
        MainActivity.database.myDao().getFeatureById(wybraneId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feature -> {
                    this.feature = feature;
                    type.setText(feature.getType());
                    idek.setText(Long.toString(feature.getId()));
                    temp = toPrettyFormat(feature.getGeometry().toString());
                    geometry.setText(temp);
                    temp = toPrettyFormat(feature.getProperties().toString());
                    properties.setText(temp);
                });

        return view;

    }
}
