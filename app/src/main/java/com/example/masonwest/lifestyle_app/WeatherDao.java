package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherData weatherData);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(WeatherData weatherData);

//    @Query("DELETE from weather_table WHERE userID = :userID")
//    void delete(int userID);
//
//    @Query("DELETE from user_table")
//    void deleteAll();

//    @Query("SELECT * from weather_table ORDER BY mLocationData ASC")
//    LiveData<List<WeatherData>> getAllWeatherData();

    @Query("SELECT Count(*) from weather_table")//LIMIT 1
    int getNumberOfWeatherData();

    @Query("SELECT * from weather_table LIMIT 1")//LIMIT 1
    WeatherData getWeatherData();
}

