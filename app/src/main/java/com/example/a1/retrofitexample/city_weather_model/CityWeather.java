package com.example.a1.retrofitexample.city_weather_model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 1 on 7/12/2016.
 */
public class CityWeather extends RealmObject{
    @PrimaryKey
    private String cityName;
    private RealmList<CityWeatherState> weatherStateList;

    public CityWeather() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<CityWeatherState> getWeatherStateList() {
        return weatherStateList;
    }

    public void setWeatherStateList(RealmList<CityWeatherState> weatherStateList) {
        this.weatherStateList = weatherStateList;
    }

    @Override
    public String toString() {
        return "CityWeather{" +
                "cityName='" + cityName + '\'' +
                ", weatherStateList=" + weatherStateList +
                '}';
    }
}
