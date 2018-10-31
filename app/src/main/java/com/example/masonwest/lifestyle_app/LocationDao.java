package com.example.masonwest.lifestyle_app;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.location.Location;

@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationData locationData);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(LocationData locationData);

    @Query("SELECT * from location_table LIMIT 1")//LIMIT 1
    LocationData getLocationData();

    //    @Query("DELETE from user_table")
    //
    //    void delete(int userID);
//    @Query("DELETE from weather_table WHERE userID = :userID")

//    void deleteAll();
//    @Query("SELECT * from weather_table ORDER BY mLocationData ASC")

//    LiveData<List<WeatherData>> getAllWeatherData();
//    @Query("SELECT Count(*) from weather_table")//LIMIT 1

//    int getNumberOfLocationData();

}
