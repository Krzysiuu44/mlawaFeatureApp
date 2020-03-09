package com.example.myapplication;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {
    @GET("/documents")
    Call<JsonElement> getDocuments(
          @Query("token") String token
    );

    @POST("/check_token")
    Call<JsonElement> addDocument(
            @Query("token") String token,
            @Body Model body
    );

    @GET("/folders/{id}")
    Call<JsonElement> getFolder(
            @Query("token") String token,
            @Path("id") String id
    );

    @POST("/login")
    Call<JsonObject> login(
            @Body JsonObject body
    );

    @POST("/check_token")
    Call<JsonObject> checkToken(
            @Query("token") String token
    );
}

//<bazowy url>/documents?token=<token>
//<bazowy url>/folders/<id>?token=<token>