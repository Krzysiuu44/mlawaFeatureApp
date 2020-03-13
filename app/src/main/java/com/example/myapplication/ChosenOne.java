package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChosenOne extends Fragment implements View.OnClickListener {

    private AdapterChoice adapterChoice;
    private RecyclerView listView;
    private Button editButtonZAdaptera;
    private Button saveButtonZAdaptera;
    private Button abortButtonZAdaptera;
    private List<Pair<String, String>> theList = Collections.emptyList();
    private List<Pair<String, String>> editableList = Collections.emptyList();

    private long wybraneId;
    private Feature feature;
    public TextView idek;
    public TextView geometry;
    //public TextView properties;
    public TextView type;

    public ChosenOne(long id) {
        wybraneId = id;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
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
        listView.setAdapter(adapterChoice);
        editButtonZAdaptera = view.findViewById(R.id.buttonEdit);
        editButtonZAdaptera.setOnClickListener(this);
        saveButtonZAdaptera = view.findViewById(R.id.buttonSave);
        saveButtonZAdaptera.setOnClickListener(this);
        abortButtonZAdaptera = view.findViewById(R.id.buttonAbort);
        abortButtonZAdaptera.setOnClickListener(this);


        type = view.findViewById(R.id.showUpType);
        idek = view.findViewById(R.id.showUpId);
        geometry = view.findViewById(R.id.showUpGeometry);
        MainActivity.database
                .myDao()
                .getFeatureById(wybraneId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feature -> {
                    this.feature = feature;
                    type.setText(feature.getType());
                    idek.setText(Long.toString(feature.getId()));
                    geometry.setText(feature.getGeometry().toString());
                    theList = build(feature.getProperties());
                    adapterChoice.setMainList(theList); // jebac frajera co u lorda andre gebe otwiera :D
                    // a tak serio to kurde.... przekaz ten result adapterowi by mogl zapisac sobie to :D
                });
        return view;

    }

    private List<Pair<String, String>> build(JsonObject temp) {
        List<String> output = Collections.emptyList();

        List<Pair<String, String>> list = temp.entrySet()
                .stream()
                .map(v -> Pair.create(v.getKey(), v != null ? v.getValue().toString() : "NULL"))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEdit:
                adapterChoice = new AdapterChoice(getContext(), editableList);
                adapterChoice.setMainList(editableList);
                if (editButtonZAdaptera.getText() == "EDIT") {
                    editButtonZAdaptera.setText("DONE");
                }
                break;
            case R.id.buttonSave:

                if (editButtonZAdaptera.getText() == "DONE") {
                    editButtonZAdaptera.setText("EDIT");
                    break;
                }
        }
    }
}
