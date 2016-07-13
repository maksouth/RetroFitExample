package com.example.a1.retrofitexample.recycler_utility;

/**
 * Created by 1 on 7/10/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1.retrofitexample.R;
import com.example.a1.retrofitexample.city_weather_model.CityWeatherState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 7/7/2016.
 */
public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherRecyclerHolder> {
    private List<CityWeatherState> states = new ArrayList<>();

    public WeatherRecyclerAdapter(List<CityWeatherState> states) {
        setState(states);
    }

    @Override
    public WeatherRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card,
                parent, false);
        WeatherRecyclerHolder holder = new WeatherRecyclerHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(WeatherRecyclerHolder holder, int position) {
        holder.bind(states.get(position));
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public void setState(List<CityWeatherState> states) {
        this.states = states;
        notifyItemRangeInserted(0, this.states.size());
    }

    public void addItem(CityWeatherState element){
        states.add(element);
        notifyItemRangeInserted(getItemCount(), this.states.size());
    }
}
