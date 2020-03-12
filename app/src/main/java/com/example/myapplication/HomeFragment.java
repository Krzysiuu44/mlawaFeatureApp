package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button bnAddModel;
    private Button bnView;
    private Button button_delete;
    private Button button_update;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //to create
        bnAddModel = view.findViewById(R.id.bn_add_user);
        bnAddModel.setOnClickListener(this);
        //to list elements
        bnView = view.findViewById(R.id.button_view);
        bnView.setOnClickListener(this);
        //to update

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bn_add_user:
                MainActivity.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new addModelFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_view:
                MainActivity.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new ViewModels())
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }
}
