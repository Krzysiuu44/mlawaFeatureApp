package com.example.myapplication;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiService {

    public static RestApi create(String url) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(url)
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(2, TimeUnit.MINUTES)
                        .readTimeout(5, TimeUnit.MINUTES)
                        .build())
                .build();

        return retrofit2.create(RestApi.class);
    }

    public static String logowanie(RestApi restApi, String login, String paass) throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("username", login);
        body.addProperty("password", paass);

        Response<JsonObject> response = restApi
                .login(body)
                .execute();

        return response.body().get("token").getAsString();
    }

    public static String pobierzToken(RestApi restApi, String oldToken) throws IOException {
        if (oldToken == null) {
            return logowanie(restApi, "aquagis", "aquagis");
        } else {
            Response<JsonObject> response = restApi
                    .checkToken(oldToken)
                    .execute();

            if (response.isSuccessful()) {
                return oldToken;
            }

            return logowanie(restApi, "aquagis", "aquagis");
        }

    }
}
