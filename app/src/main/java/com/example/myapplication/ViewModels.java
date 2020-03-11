package com.example.myapplication;

import android.annotation.SuppressLint;
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

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
    private List<Feature> thatList = Collections.emptyList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_models, container, false);

        //   Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
        //         Toast.makeText(getActivity(), "example models added successfully", Toast.LENGTH_SHORT).show();
        //   });
        adapter = new Adapter(getContext(), thatList);

        list = view.findViewById(R.id.RecyclerView1);
        list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list.setAdapter(adapter);

        wyswietl();

        return view;
    }

    @SuppressLint("CheckResult")
    private Completable pobiezZWeb() {
        return Completable.create(emitter -> {
            try {
                Response<JsonElement> response = MainActivity.restApi.getDocuments(MainActivity.token).execute();
                Model model = Model.parse(response.body());

                Completable.complete()
                        .andThen(addToDatebase(model.features))
                        .andThen(pobiezZDB())
                        .subscribe(
                                emitter::onComplete,
                                emitter::onError
                        );

            } catch (IOException e) {
                e.printStackTrace();
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io());
    }

    @SuppressLint("CheckResult")
    private Completable pobiezZDB() {
        return Completable.create(emitter -> {
            MainActivity.database
                    .myDao()
                    .getList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(features -> { // a tu dajemy co mamy z nim zrobic, tu wstawiamy do adaptera
                        setToAdapter(features);
                        emitter.onComplete();
                    });
        })
                .subscribeOn(Schedulers.io());
    }

    @SuppressLint("CheckResult")
    private void wyswietl() {
        MainActivity.database
                .myDao()
                .count()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapCompletable(count -> {
                    if (count == 0) {
                        return pobiezZWeb();
                    } else {
                        return pobiezZDB();
                    }
                })
                .subscribe(() -> {
                    System.out.println("Załadowano");
                });
    }

    @SuppressLint("CheckResult")
    private Completable addToDatebase(List<Feature> list) {
        return MainActivity.database.myDao()
                .addModel(list)
                .subscribeOn(Schedulers.io())
                .ignoreElement(); //ignorujemy bo nie potrzebujemy z tym nic dalej robic
    }

    private void setToAdapter(List<Feature> list) {
        adapter.setList(list);
    }
}
