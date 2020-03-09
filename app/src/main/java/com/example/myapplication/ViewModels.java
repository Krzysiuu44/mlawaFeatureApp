package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewModels extends Fragment {

    public ViewModels() {
        // Required empty public constructor
    }


    private RecyclerView list;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_models, container, false);

        //   Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
        //         Toast.makeText(getActivity(), "example models added successfully", Toast.LENGTH_SHORT).show();
        //   });
        adapter = new Adapter(getContext(), thatList);

        list = view.findViewById(R.id.RecyclerView);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

        wyswietl();

        return view;
    }

    private List<Feature> thatList = Collections.emptyList();

    private void pobiezZWeb() {
        try {
            Response<JsonElement> response = MainActivity.restApi.getDocuments(MainActivity.token).execute();
            Model model = Model.parse(response.body());

            addToDatebase(model.features);

            pobiezZDB();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pobiezZDB() {
        List<Feature> list = MainActivity.database.myDao().getList();

        if (list == null) {
            list = Collections.emptyList();
        }

        setToAdapter(list);
    }

    private void wyswietl() {
        long count = MainActivity.database.myDao().count();
        if (count == 0) {
            pobiezZWeb();
        } else {
            pobiezZDB();
        }
    }

    private void addToDatebase(List<Feature> list) {
        MainActivity.database.myDao().addModel(list);
    }

    private void setToAdapter(List<Feature> list) {
        adapter.setList(list);
    }
}
