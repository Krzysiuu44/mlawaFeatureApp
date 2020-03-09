package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment {
    private EditText modelGeomety, modelProperties, modelType;
    private Button bnSave;
    private JsonObject a, b;
    public UpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        modelGeomety = view.findViewById(R.id.txt_model_geometry);
        modelProperties = view.findViewById(R.id.txt_model_properties);
        modelType = view.findViewById(R.id.txt_model_type);
        bnSave = view.findViewById(R.id.bn_save_model);

        return view;
    }
}
