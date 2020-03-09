package com.example.myapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static FragmentManager fragmentManager;
    public static database database;
    public static RestApi restApi;
    public static String token;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        database = RoomConstant.buildRoomDb(this);
        restApi = RestApiService.create("https://mlawa.gis.support");


        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            fragmentManager.beginTransaction().add(R.id.fragment_container, new HomeFragment()).commit();
        }

        /*long count = RoomConstant.buildRoomDb(this).myDao().count();
        if (count!=0) {
            List<Feature> featuresAllView = RoomConstant.buildRoomDb(this).myDao().getList();
        }*/

        try {
            token = RestApiService.pobierzToken(restApi, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void wykonaj() {
        RestApi restApi = RestApiService.create("https://mlawa.gis.support");

        restApi
                .getDocuments("token")
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Model model = Model.parse(response.body());
                        //model.features;
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });

        JsonObject body = new JsonObject();
        body.addProperty("login", "aquagis");
        body.addProperty("password", "aquagis");

        restApi
                .login(body)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        String token = response.body().get("token").getAsString();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });


        restApi
                .checkToken("token")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        String token = response.body().get("token").getAsString();

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }


    @Override
    public void onClick(View v) {

    }


}
