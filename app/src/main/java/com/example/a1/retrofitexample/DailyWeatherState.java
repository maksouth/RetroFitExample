package com.example.a1.retrofitexample;

import java.util.Date;

/**
 * Created by 1 on 7/11/2016.
 */
public class DailyWeatherState {
    public static final int THOUSAND = 1000;

    private Date date;
    private double temperature;
    private double pressure;
    private double windSpeed;
    private String description;
    private int iconId;

    public DailyWeatherState(long dateInSeconds, double temperature, double pressure, double windSpeed, String description, int iconId) {
        this.date = new Date(dateInSeconds*THOUSAND);
        this.temperature = temperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.description = description;
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public Date getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getDescription() {
        return description;
    }
}
