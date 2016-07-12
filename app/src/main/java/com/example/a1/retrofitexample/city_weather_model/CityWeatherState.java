package com.example.a1.retrofitexample.city_weather_model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 1 on 7/12/2016.
 */
public class CityWeatherState extends RealmObject {
    @Ignore
    public static final int THOUSAND = 1000;

    @PrimaryKey
    private long hash;

    private long date;
    private Temperature temperature;
    private double pressure;
    private double windSpeed;
    private String description;
    private int iconId;
    @Ignore
    private String city = "";

    public CityWeatherState(long dateInSeconds, Temperature temperature, double pressure, double windSpeed, String description, int iconId) {
        this.date = dateInSeconds*THOUSAND;
        this.temperature = temperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.description = description;
        this.iconId = iconId;
        generateHash();
    }

    public CityWeatherState() {
    }

    public void setCity(String city) {
        this.city = city;
        generateHash();
    }

    public long getHash() {
        return hash;
    }

    public void setDate(long date) {
        this.date = date;
        generateHash();
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public long getDate() {
        return date;
    }

    public Temperature getTemperature() {
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

    public Date getDateAsObject(){
        return new Date(date);
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "CityWeatherState{" +
                "hash=" + hash +
                ", date=" + date +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", windSpeed=" + windSpeed +
                ", description='" + description + '\'' +
                ", iconId=" + iconId +
                '}';
    }

    public void generateHash(){
        hash = date+city.hashCode();
    }
}

