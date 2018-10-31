package com.example.masonwest.lifestyle_app;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "location_table")
public class LocationData {
    @PrimaryKey
    @NonNull
    private String mCity;
    private String mCountry;
    private double mLatitude;
    private double mLongitude;

    private long mSunset;
    private long mSunrise;

    public double getLatitude() {
        return mLatitude;
    }
    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public long getSunset() {
        return mSunset;
    }
    public void setSunset(long sunset){
        mSunset = sunset;
    }
    public long getSunrise() {
        return mSunrise;
    }
    public void setSunrise(long sunrise) {
        mSunrise = sunrise;
    }
    public String getCountry() {
        return mCountry;
    }
    public void setCountry(String country) {
        mCountry = country;
    }
    public String getCity() {
        return mCity;
    }
    public void setCity(String city) {
        mCity = city;
    }
}
