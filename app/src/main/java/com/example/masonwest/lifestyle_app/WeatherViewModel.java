package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<WeatherData> jsonData;
    private UserRepository mUserRepositoryForWeather;

    public WeatherViewModel(Application application){
        super(application);
        mUserRepositoryForWeather = new UserRepository(application);
        jsonData = mUserRepositoryForWeather.getData();
    }

    public void setLocation(String location){
        mUserRepositoryForWeather.setLocation(location);
    }

    public LiveData<WeatherData> getData(){
        return jsonData;
    }


}

