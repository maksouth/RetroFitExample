package com.example.a1.retrofitexample.city_weather_model;

import io.realm.RealmObject;

/**
 * Created by 1 on 7/12/2016.
 */
public class Temperature extends RealmObject {
    private Double day;
    private Double min;
    private Double max;
    private Double night;
    private Double eve;
    private Double morn;


    public Temperature() {
    }

    /**
     *
     * @return
     * The day
     */
    public Double getDay() {
        return day;
    }

    /**
     *
     * @param day
     * The day
     */
    public void setDay(Double day) {
        this.day = day;
    }

    /**
     *
     * @return
     * The min
     */
    public Double getMin() {
        return min;
    }

    /**
     *
     * @param min
     * The min
     */
    public void setMin(Double min) {
        this.min = min;
    }

    /**
     *
     * @return
     * The max
     */
    public Double getMax() {
        return max;
    }

    /**
     *
     * @param max
     * The max
     */
    public void setMax(Double max) {
        this.max = max;
    }

    /**
     *
     * @return
     * The night
     */
    public Double getNight() {
        return night;
    }

    /**
     *
     * @param night
     * The night
     */
    public void setNight(Double night) {
        this.night = night;
    }

    /**
     *
     * @return
     * The eve
     */
    public Double getEve() {
        return eve;
    }

    /**
     *
     * @param eve
     * The eve
     */
    public void setEve(Double eve) {
        this.eve = eve;
    }

    /**
     *
     * @return
     * The morn
     */
    public Double getMorn() {
        return morn;
    }

    /**
     *
     * @param morn
     * The morn
     */
    public void setMorn(Double morn) {
        this.morn = morn;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "day=" + day +
                ", min=" + min +
                ", max=" + max +
                ", night=" + night +
                ", eve=" + eve +
                ", morn=" + morn +
                '}';
    }
}
