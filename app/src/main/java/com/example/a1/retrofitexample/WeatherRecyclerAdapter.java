package com.example.a1.retrofitexample;

/**
 * Created by 1 on 7/10/2016.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 7/7/2016.
 */
public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherRecyclerHolder> {
    private List<DailyWeatherState> states = new ArrayList<>();

    public WeatherRecyclerAdapter(List<DailyWeatherState> states) {
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

    public void setState(List<DailyWeatherState> states) {
        this.states = states;
        notifyItemRangeInserted(0, this.states.size());
    }

    public void addItem(DailyWeatherState element){
        states.add(element);
        notifyItemRangeInserted(getItemCount(), this.states.size());
    }
}
