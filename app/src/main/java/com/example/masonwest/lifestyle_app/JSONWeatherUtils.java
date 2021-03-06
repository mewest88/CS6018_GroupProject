package com.example.masonwest.lifestyle_app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Declare methods as static. We don't want to create objects of this class.
public class JSONWeatherUtils {
    public static WeatherData getWeatherData(String data) throws JSONException{
        WeatherData weatherData = new WeatherData();

        //Start parsing JSON data
        JSONObject jsonObject = new JSONObject(data); //Must throw JSONException

//        WeatherData.CurrentCondition currentCondition = weatherData.getCurrentCondition();
        CurrentCondition currentCondition = weatherData.getCurrentCondition();
        JSONObject jsonMain = jsonObject.getJSONObject("main");
        currentCondition.setHumidity(jsonMain.getInt("humidity"));
        currentCondition.setPressure(jsonMain.getInt("pressure"));
        weatherData.setCurrentCondition(currentCondition);

        //Location stuff
        LocationData locationData = weatherData.getLocationData();
        JSONObject jsonCoord = jsonObject.getJSONObject("coord");
        locationData.setLongitude(jsonCoord.getLong("lon"));
        locationData.setLatitude(jsonCoord.getLong("lat"));
        JSONObject jsonSys = jsonObject.getJSONObject("sys");
        locationData.setCountry(jsonSys.getString("country"));
        locationData.setSunrise(jsonSys.getLong("sunrise"));
        locationData.setSunset(jsonSys.getLong("sunset"));
        locationData.setCity(jsonObject.getString("name"));
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