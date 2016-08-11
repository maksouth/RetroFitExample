package com.example.a1.retrofitexample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.retrofitexample.city_weather_model.CityWeather;
import com.example.a1.retrofitexample.city_weather_model.CityWeatherState;
import com.example.a1.retrofitexample.city_weather_model.Temperature;
import com.example.a1.retrofitexample.recycler_utility.WeatherRecyclerAdapter;
import com.example.a1.retrofitexample.retrofit_api.ApiFactory;
import com.example.a1.retrofitexample.retrofit_api.RestInterface;
import com.example.a1.retrofitexample.retrofit_pojo_model.Example;
import com.example.a1.retrofitexample.utility.DataModelAdapter;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import retrofit.Callback;
import retrofit.RetrofitError;

public class MainActivity extends AppCompatActivity {

    public static final int CURRENT_WEATHER_ELEMENT = 0;
    public static final int NEXT_DAY_FORECAST_ELEMENT = 1;
    public static final String DEFAULT_CITY_NAME = "Zhytomyr";
    public static final int DEFAULT_DAYS_FORECAST = 10;
    public static final String ERROR_NO_FOUND_CITY = "Error: Not found city";
    public static final int MIN_DAYS_FORECAST = 1;
    public static final int MAX_DAYS_FORECAST = 16;
    public static final String EMPTY_STRING="";

    static boolean isConnectedToWeatherAPI = false;

    ProgressDialog serverConnectDialog;
    RecyclerView recycler;
    LinearLayoutManager verticalManager;
    TextView currentPressureTV;
    TextView currentDescriptionTV;
    TextView currentWindSpeedTV;
    TextView currentMornTempTV;
    TextView currentNightTempTV;
    TextView currentDayTempTV;
    TextView currentEveTempTV;
    TextView cityNameTv;
    ImageView currentWeatherIconIV;

    EditText cityNameET;
    EditText daysForecastET;
    Button acceptInputButton;

    Realm realm;
    static String city;

    static{
        city = DEFAULT_CITY_NAME.toLowerCase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initializeUIElements();
        connectToDB();
        //removeOldRecordsFromDB(cityName);


        loadData(city, DEFAULT_DAYS_FORECAST);

    }

    /**
     * fill UI components for displaying weather information from dataModel
     * @param dataModel
     */
    public void fillUIFromResponse(CityWeather dataModel){

        Log.e("FILL UI", dataModel.getCityName());

        CityWeatherState element;

        cityNameTv.setText(dataModel.getCityName());

        element = dataModel.getWeatherStateList().get(CURRENT_WEATHER_ELEMENT);
        currentDescriptionTV.setText(element.getDescription());
        currentWindSpeedTV.setText(String.valueOf(element.getWindSpeed()));
        currentPressureTV.setText(String.valueOf(element.getPressure()));
        currentNightTempTV.setText(String.valueOf(element.getTemperature().getNight()));
        currentMornTempTV.setText(String.valueOf(element.getTemperature().getMorn()));
        currentDayTempTV.setText(String.valueOf(element.getTemperature().getDay()));
        currentEveTempTV.setText(String.valueOf(element.getTemperature().getEve()));
        currentWeatherIconIV.setImageResource(element.getIconId());

        WeatherRecyclerAdapter recyclerAdapter = new WeatherRecyclerAdapter(dataModel
                                                        .getWeatherStateList()
                                                        .subList(NEXT_DAY_FORECAST_ELEMENT,
                                                                dataModel.getWeatherStateList().size()));
        recycler.setAdapter(recyclerAdapter);
    }

    /**
     * Initialize all UI elements, add listeners to buttons and describe their logic
     */
    public void initializeUIElements(){
        recycler = (RecyclerView)findViewById(R.id.recycler);
        verticalManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(verticalManager);

        serverConnectDialog = new ProgressDialog(MainActivity.this);
        serverConnectDialog.setTitle("Please wait...");

        currentDayTempTV = (TextView)findViewById(R.id.day_temp_tv);
        currentNightTempTV = (TextView)findViewById(R.id.night_temp_tv);
        currentEveTempTV = (TextView)findViewById(R.id.eve_temp_tv);
        currentMornTempTV = (TextView)findViewById(R.id.morn_temp_tv);
        currentPressureTV = (TextView)findViewById(R.id.current_pressure_tv);
        currentWindSpeedTV = (TextView)findViewById(R.id.current_wind_speed_tv);
        currentDescriptionTV = (TextView)findViewById(R.id.current_description_tv);
        cityNameTv = (TextView) findViewById(R.id.city_name_tv);
        currentWeatherIconIV = (ImageView) findViewById(R.id.current_weather_icon);

        cityNameET = (EditText) findViewById(R.id.city_name_et);
        daysForecastET = (EditText) findViewById(R.id.days_forecast_et);
        acceptInputButton = (Button) findViewById(R.id.accept_input_button);
        acceptInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer daysForecast;
                if( !( isEmpty(cityNameET.getText()) || isEmpty(daysForecastET.getText() ) ) ){
                    try {
                        daysForecast = Integer.valueOf(String.valueOf(daysForecastET.getText()));
                    }catch(Exception ex){
                        ex.printStackTrace();
                        daysForecast = 5;
                        Toast.makeText(MainActivity.this, "Number is too large. Default = 5", Toast.LENGTH_LONG).show();
                    }

                    if(!isForecastDiapason(daysForecast)){
                        Toast.makeText(MainActivity.this, "Input forecast days between 1 and 16", Toast.LENGTH_LONG).show();
                    }else {
                        loadData(String.valueOf(cityNameET.getText()), daysForecast);
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Input correct city name or forecast days", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Check whether string is empty (containing "" value)
     * @param sequence
     * @return
     */
    public boolean isEmpty(Editable sequence){
        return String.valueOf(sequence).equals(EMPTY_STRING);
    }

    /**
     * Check whether @param (days) is in correct day range to forecast (1-16)
     * @param days
     * @return
     */
    public boolean isForecastDiapason(int days){
        return (days>=MIN_DAYS_FORECAST && days<=MAX_DAYS_FORECAST);
    }

    public void makeApiCall(String cityNameParameter, int daysForecastParameter){

        serverConnectDialog.show();

        RestInterface restInterface = ApiFactory.getWeatherService();
        //Calling method to get whether report
        restInterface.getWheatherReport(cityNameParameter, daysForecastParameter,  new Callback<Example>() {

            @Override
            public void success(Example model, retrofit.client.Response response) {
                Log.e("SUCCESS", model.toString());
                Log.e("SUCCESS", String.valueOf(response.getStatus()));
                if(response.getStatus()>=200 && response.getStatus()<300) {
                    CityWeather dataModel = DataModelAdapter.convertToCityWeather(model);;
                    city = dataModel.getCityName();

                    writeCityWeatherToDB(dataModel);
                    fillUIFromResponse(dataModel);
                }else{
                    Toast.makeText(MainActivity.this, "Error with server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("FATAL", error.getMessage() + " " + error.getKind());
                if(error.getMessage().contains(ERROR_NO_FOUND_CITY)){
                    Toast.makeText(MainActivity.this, "No city founds", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Error with getting connection. Check your connection.", Toast.LENGTH_LONG).show();
                }

                error.printStackTrace();
            }
        });

        serverConnectDialog.dismiss();
    }

    /**
     * set settings
     */
    public void connectToDB(){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(MainActivity.this)
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();
    }

    /**
     * wrtite CityWeather object to DB, use cityName as primary key
     * @param dataModel
     */
    public void writeCityWeatherToDB(CityWeather dataModel){

        realm.beginTransaction();

        RealmList<CityWeatherState> states = new RealmList<>();
        for(CityWeatherState state: dataModel.getWeatherStateList()){
            Temperature rTemp = realm.copyToRealm(state.getTemperature());
            state.setTemperature(rTemp);
            states.add(realm.copyToRealmOrUpdate(state));
        }
        dataModel.setWeatherStateList(states);
        realm.copyToRealmOrUpdate(dataModel);
        realm.commitTransaction();
    }

    /**
     * read CityWeather object from DB
     * @param cityName
     * @return
     */
    public CityWeather readCityWeatherFromDB(String cityName){
        Log.e("READ DB", cityName);
        CityWeather cityWeather = realm.where(CityWeather.class).equalTo("cityName", cityName).findFirst();
        //Log.e("REALM", cityWeather.toString());
        return cityWeather;
    }

    /**
     * firstly get data from DB (readCityWeatherFromDB) and put this data to UI
     * load data from server(makeApiCall), write it to database (writeCityWeatherToDB) and
     * put this data to UI(fiiUIFromResponse). If server isn't available, or there is no connection
     * makes an attempt to get data from db
     * @param cityName - city to get forecast of
     * @param daysForecast - number of frecasting days
     */
    public void loadData(String cityName, int daysForecast){
        cityName = cityName.toLowerCase();

        CityWeather weather = readCityWeatherFromDB(cityName);
        if(weather!=null){
            fillUIFromResponse(weather);
        }

        if(!isConnectedToWeatherAPI || !city.equals(cityName)) {
            city = cityName;
            Log.e("LOAD", cityName);
            makeApiCall(cityName, daysForecast);
            isConnectedToWeatherAPI = true;
        }else{
            city = cityName;
        }
        Log.e("LOAD", "after api call");
    }

}
