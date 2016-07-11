package com.example.a1.retrofitexample;

import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 1 on 7/10/2016.
 */
public class WeatherRecyclerHolder extends RecyclerView.ViewHolder {
    TextView temperature;
    TextView pressure;
    TextView wind;
    TextView weather;
    TextView date;
    ImageView icon;

    public WeatherRecyclerHolder(View itemView) {
        super(itemView);
        temperature = (TextView) itemView.findViewById(R.id.temperature);
        pressure = (TextView) itemView.findViewById(R.id.pressure);
        wind = (TextView) itemView.findViewById(R.id.wind);
        weather = (TextView) itemView.findViewById(R.id.weather);
        date = (TextView) itemView.findViewById(R.id.date);
        icon = (ImageView) itemView.findViewById(R.id.icon_weather);
    }

    public void bind(DailyWeatherState element){
        temperature.setText(String.valueOf(element.getTemperature()));
        pressure.setText(String.valueOf(element.getPressure()));
        wind.setText(String.valueOf(element.getWindSpeed()));
        weather.setText(String.valueOf(element.getDescription()));
        date.setText(DateFormat.format("dd.MM.yyyy", element.getDate()));
        icon.setImageResource(element.getIconId());
    }
}