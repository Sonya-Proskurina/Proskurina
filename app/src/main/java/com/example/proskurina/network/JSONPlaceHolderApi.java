package com.example.proskurina.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {

    @GET("/top/{id}?json=true")
    public Call<BigData> getPostTop(@Path("id") int id);

    @GET("/latest/{id}?json=true")
    public Call<BigData> getPostLatest(@Path("id") int id);

    @GET("/hot/{id}?json=true")
    public Call<BigData> getPostHot(@Path("id") int id);

}