package com.example.a1.retrofitexample;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;


/**
 * Created by 1 on 7/12/2016.
 */
public class ApiFactory {
    private static OkHttpClient client = new OkHttpClient();
    public static final String URL = "http://api.openweathermap.org/data/2.5";
    public static final int CONNECTION_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 60;
    public static final int READ_TIMEOUT = 60;


    static {
        client.setConnectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        client.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        client.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
    }


    public static RestInterface getWeatherService() {
        return getRestAdapter().create(RestInterface.class);
    }

    private static RestAdapter getRestAdapter(){
        return new RestAdapter.Builder().setClient(new OkClient(client)).setEndpoint(URL).build();
    }

}
