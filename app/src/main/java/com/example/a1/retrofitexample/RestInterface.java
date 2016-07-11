package com.example.a1.retrofitexample;



import com.example.a1.retrofitexample.POJO3.Example;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by 1 on 7/10/2016.
 */
public interface RestInterface {

    @GET("/forecast/daily?appid=6b5c68919bb64aa5793f14196102c0e1&cnt=10&units=metric")
    void getWheatherReport(@Query("q") String q, Callback<Example> cb);



}