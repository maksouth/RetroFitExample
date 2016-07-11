package com.example.a1.retrofitexample;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by 1 on 7/10/2016.
 */
public class WeatherState {
    private String description;
    private double temperature;
    private double wind;
    private double pressure;
    private long date;

    public WeatherState(long date,String description, double temperature, double wind, double pressure) {
        this.date = date*1000;
        this.description = description;
        this.temperature = temperature;
        this.wind = wind;
        this.pressure = pressure;
    }

    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWind() {
        return wind;
    }

    public double getPressure() {
        return pressure;
    }

    public int getIconId(){
        return 0;
    }

    public Date getDate() {
        return new Date(date);
    }
}
