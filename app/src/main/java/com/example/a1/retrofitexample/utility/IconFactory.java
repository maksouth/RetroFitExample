package com.example.a1.retrofitexample.utility;

import com.example.a1.retrofitexample.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 1 on 7/11/2016.
 */
public class IconFactory {

    private static Map<String, Integer> map = new HashMap<>();

    static {
        map.put("01", R.drawable.sun);
        map.put("02", R.drawable.sun_cloud);
        map.put("03", R.drawable.cloud);
        map.put("04", R.drawable.broken_clouds);
        map.put("09", R.drawable.shower_rain);
        map.put("10", R.drawable.rain_sun);
        map.put("11", R.drawable.thunderstoprm);
        map.put("13", R.drawable.snow);
        map.put("50", R.drawable.mist);

    }

    public static int getIconId(String id){
        return map.get(id.substring(0, id.length()-1));
    }
}
