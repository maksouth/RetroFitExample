package com.example.a1.retrofitexample;

import com.example.a1.retrofitexample.city_weather_model.CityWeather;
import com.example.a1.retrofitexample.city_weather_model.CityWeatherState;
import com.example.a1.retrofitexample.city_weather_model.Temperature;
import com.example.a1.retrofitexample.retrofit_pojo_model.Example;
import com.example.a1.retrofitexample.retrofit_pojo_model.List;
import com.example.a1.retrofitexample.retrofit_pojo_model.Temp;

import io.realm.RealmList;

/**
 * Created by 1 on 7/12/2016.
 */
public class DataModelAdapter {
    static CityWeather convertToCityWeather(Example example){
        CityWeather cityWeather = new CityWeather();
        RealmList<CityWeatherState> weatherStateList = new RealmList<>();

        cityWeather.setCityName(example.getCity().getName());
        for(List element: example.getList()){
            CityWeatherState state = convertToCityWeatherState(element);
            state.setCity(cityWeather.getCityName());
            weatherStateList.add(state);
        }

        cityWeather.setWeatherStateList(weatherStateList);
        return cityWeather;
    }

    static Temperature convertToTemperature(Temp temp){
        Temperature temperature = new Temperature();
        temperature.setDay(temp.getDay());
        temperature.setNight(temp.getNight());
        temperature.setEve(temp.getEve());
        temperature.setMax(temp.getMax());
        temperature.setMin(temp.getMin());
        temperature.setMorn(temp.getMorn());
        return temperature;
    }

    /**
     * do not forget that you should set city name (for accurate calculating of hash code) separately
     * @param element
     * @return
     */
    static CityWeatherState convertToCityWeatherState(List element){
        return new CityWeatherState(element.getDt(),
                convertToTemperature(element.getTemp()),
                element.getPressure(),
                element.getSpeed(),
                element.getWeather().get(0).getDescription(),
                IconFactory.getIconId(element.getWeather()
                        .get(0).getIcon()));
    }
}
