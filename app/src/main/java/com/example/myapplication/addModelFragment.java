package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class addModelFragment extends Fragment {
    private EditText modelGeomety, modelProperties, modelType;
    private Button bnSave;
    private JsonObject a, b;

    public addModelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_model, container, false);

        modelGeomety = view.findViewById(R.id.txt_model_geometry);
        modelProperties = view.findViewById(R.id.txt_model_properties);
        modelType = view.findViewById(R.id.txt_model_type);
        bnSave = view.findViewById(R.id.bn_save_model);

        bnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.bn_save_model:
                        String modelGeo = modelGeomety.getText().toString();
                        String modelPro = modelProperties.getText().toString();
                        String modelTyp = modelType.getText().toString();
                        JsonParser parser = new JsonParser();
                        a = (JsonObject) parser.parse(modelGeo);
                        b = (JsonObject) parser.parse(modelPro);
                        Feature nowy = new Feature(a, b, modelTyp);

                        addToDatebase(nowy);
                        break;
                }
            }
        });
        return view;
    }

    @SuppressLint("CheckResult")
    private void addToDatebase(Feature feature) {
        Toast.makeText(getActivity(), "Dodawanie w trakcie", Toast.LENGTH_SHORT).show();
        MainActivity.database.myDao()
                .addModel(feature)
                .observeOn(AndroidSchedulers.mainThread())// wymaga dostepu do main freda
                .subscribe(() -> {
                    Toast.makeText(getActivity(), "dodawanie zakończone sukcesem", Toast.LENGTH_SHORT).show();

                    modelGeomety.setText("");
                    modelProperties.setText("");
                    modelType.setText("");

                    MainActivity.fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, new HomeFragment())
                            .addToBackStack(null)
                            .commit();
                });
    }
}
