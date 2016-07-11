package com.example.a1.retrofitexample;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.retrofitexample.POJO3.Example;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://api.openweathermap.org/data/2.5";
    public static final int CURRENT_WEATHER_ELEMENT = 0;
    public static final int NEXT_DAY_FORECAST_ELEMENT = 1;
    public static final int ZERO_WEATHER_INDEX = 0;


    static boolean isConnectedToWeatherAPI = false;

    RecyclerView recycler;
    LinearLayoutManager verticalManager;
    TextView currentPressureTV;
    TextView currentDescriptionTV;
    TextView currentWindSpeedTV;
    TextView currentMornTempTV;
    TextView currentNightTempTV;
    TextView currentDayTempTV;
    TextView currentEveTempTV;
    TextView cityNameTv;
    ImageView currentWeatherIconIV;

    static Example dataModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initializeUIElements();


        if(!isConnectedToWeatherAPI) {
            Toast.makeText(this, "Successfully connected", Toast.LENGTH_SHORT).show();
            //making object of RestAdapter
            RestAdapter adapter = new RestAdapter.Builder().setEndpoint(URL).build();

            //Creating Rest Services
            RestInterface restInterface = adapter.create(RestInterface.class);

            //Calling method to get whether report
            restInterface.getWheatherReport("Zhytomyr,ua", new Callback<Example>() {

                @Override
                public void success(Example model, retrofit.client.Response response) {
                    Log.e("SUCCESS", model.toString());
                    dataModel = model;
                    isConnectedToWeatherAPI = true;
                    fillUIFromResponse();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("FATAL", error.getMessage());
                }
            });

        }else{fillUIFromResponse();}
    }

    public void fillUIFromResponse(){

        com.example.a1.retrofitexample.POJO3.List element;

        cityNameTv.setText(dataModel.getCity().getName());

        element = dataModel.getList().get(CURRENT_WEATHER_ELEMENT);
        currentDescriptionTV.setText(element.getWeather().get(ZERO_WEATHER_INDEX).getDescription());
        currentWindSpeedTV.setText(String.valueOf(element.getSpeed()));
        currentPressureTV.setText(String.valueOf(element.getPressure()));
        currentNightTempTV.setText(String.valueOf(element.getTemp().getNight()));
        currentMornTempTV.setText(String.valueOf(element.getTemp().getMorn()));
        currentDayTempTV.setText(String.valueOf(element.getTemp().getDay()));
        currentEveTempTV.setText(String.valueOf(element.getTemp().getEve()));
        currentWeatherIconIV.setImageResource(IconFactory.getIconId(element.getWeather().get(0).getIcon()));

        List<DailyWeatherState> stateList = new ArrayList<>();
        DailyWeatherState state;
        for (int i = NEXT_DAY_FORECAST_ELEMENT; i < dataModel.getList().size(); i++) {
            element = dataModel.getList().get(i);
            state = new DailyWeatherState(element.getDt(),
                    element.getTemp().getDay(),
                    element.getPressure(),
                    element.getSpeed(),
                    element.getWeather().get(ZERO_WEATHER_INDEX).getDescription(),
                    IconFactory.getIconId(element.getWeather().get(ZERO_WEATHER_INDEX).getIcon()));
            stateList.add(state);
        }

        WeatherRecyclerAdapter recyclerAdapter = new WeatherRecyclerAdapter(stateList);
        recycler.setAdapter(recyclerAdapter);
    }

    public void initializeUIElements(){
        recycler = (RecyclerView)findViewById(R.id.recycler);
        verticalManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(verticalManager);

        currentDayTempTV = (TextView)findViewById(R.id.day_temp_tv);
        currentNightTempTV = (TextView)findViewById(R.id.night_temp_tv);
        currentEveTempTV = (TextView)findViewById(R.id.eve_temp_tv);
        currentMornTempTV = (TextView)findViewById(R.id.morn_temp_tv);
        currentPressureTV = (TextView)findViewById(R.id.current_pressure_tv);
        currentWindSpeedTV = (TextView)findViewById(R.id.current_wind_speed_tv);
        currentDescriptionTV = (TextView)findViewById(R.id.current_description_tv);
        cityNameTv = (TextView) findViewById(R.id.city_name_tv);
        currentWeatherIconIV = (ImageView) findViewById(R.id.current_weather_icon);
    }



}
