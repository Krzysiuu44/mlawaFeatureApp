package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


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

    public ChosenOne(long id) {
        wybraneId = id;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chosen_one, container, false);
        type = view.findViewById(R.id.showUpType);
        idek = view.findViewById(R.id.showUpId);
        geometry = view.findViewById(R.id.showUpGeometry);
        properties = view.findViewById(R.id.showUpProperties);
        feature = MainActivity.database.myDao().getFeatureById(wybraneId);
        type.setText(feature.getType());
        idek.setText(Long.toString(feature.getId()));
        geometry.setText(feature.getGeometry().toString());
        properties.setText(feature.getProperties().toString());
        return view;

    }
}
