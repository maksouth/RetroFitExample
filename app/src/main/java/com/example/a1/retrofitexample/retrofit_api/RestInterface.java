package com.example.a1.retrofitexample.retrofit_api;



import com.example.a1.retrofitexample.retrofit_pojo_model.Example;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by 1 on 7/10/2016.
 */
public interface RestInterface {

    @GET("/forecast/daily?appid=6b5c68919bb64aa5793f14196102c0e1&units=metric")
    void getWheatherReport(@Query("q") String q, @Query("cnt") int d, Callback<Example> cb);

}