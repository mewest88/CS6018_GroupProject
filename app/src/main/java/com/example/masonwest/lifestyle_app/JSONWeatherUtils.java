package com.example.masonwest.lifestyle_app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Declare methods as static. We don't want to create objects of this class.
public class JSONWeatherUtils {
    public static WeatherData getWeatherData(String data) throws JSONException{
        WeatherData weatherData = new WeatherData();
        LocationData locationData = new LocationData();

        //Start parsing JSON data
        JSONObject jsonObject = new JSONObject(data); //Must throw JSONException

        WeatherData.CurrentCondition currentCondition = weatherData.getCurrentCondition();
        JSONObject jsonMain = jsonObject.getJSONObject("main");
        currentCondition.setHumidity(jsonMain.getInt("humidity"));
        currentCondition.setPressure(jsonMain.getInt("pressure"));
        weatherData.setCurrentCondition(currentCondition);

        //Location stuff
        locationData.setLongitude(jsonMain.getLong("lon"));
        locationData.setLatitude(jsonMain.getLong("lat"));
        locationData.setCity(jsonMain.getString("name"));
        locationData.setCountry(jsonMain.getString("country"));
        locationData.setSunrise(jsonMain.getLong("sunrise"));
        locationData.setSunset(jsonMain.getLong("sunset"));
        weatherData.setLocationData(locationData);

        //Get the temperature, wind and cloud data.
        WeatherData.Temperature temperature = weatherData.getTemperature();
        WeatherData.Wind wind = weatherData.getWind();
        WeatherData.Clouds clouds = weatherData.getClouds();
        temperature.setMaxTemp(jsonMain.getDouble("temp_max"));
        temperature.setMinTemp(jsonMain.getDouble("temp_min"));
        temperature.setTemp(jsonMain.getDouble("temp"));
        weatherData.setTemperature(temperature);

        return weatherData;
    }
}