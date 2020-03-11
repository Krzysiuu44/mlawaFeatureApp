package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChosenOne extends Fragment {

    private AdapterChoice adapterChoice;
    private RecyclerView listView;
    private List<Pair<String, String>> theList = Collections.emptyList();


    private long wybraneId;
    private Feature feature;
    public TextView idek;
    public TextView geometry;
    public TextView properties;
    public TextView type;
    public String temp;
    public JsonObject aaa;

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

        adapterChoice = new AdapterChoice(getContext(), theList);
        listView = view.findViewById(R.id.showUpProperties);
        listView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //listView.setAdapter((ListAdapter) adapterChoice);
        listView.setAdapter(adapterChoice);


        type = view.findViewById(R.id.showUpType);
        idek = view.findViewById(R.id.showUpId);
        geometry = view.findViewById(R.id.showUpGeometry);
        MainActivity.database.myDao().getFeatureById(wybraneId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feature -> {
                    this.feature = feature;
                    type.setText(feature.getType());
                    idek.setText(Long.toString(feature.getId()));
                    aaa = feature.getGeometry();
                    temp = build(aaa).toString();
                    geometry.setText(temp);
                    //  temp = toPrettyFormat(feature.getProperties().toString());
                    //   properties.setText(temp);
                });

        return view;

    }

    private List<Pair<String, String>> build(JsonObject temp) {
        List<String> output = Collections.emptyList();

        List<Pair<String, String>> list = temp.entrySet()
                .stream()
                .map(v -> Pair.create(v.getKey(), v != null ? v.toString() : "NULL"))
                .collect(Collectors.toList());

        return list;
    }
}
