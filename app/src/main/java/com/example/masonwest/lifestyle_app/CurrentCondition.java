package com.example.masonwest.lifestyle_app;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "currentcondition_table")
public  class CurrentCondition {
    @PrimaryKey
    @NonNull
    private long mWeatherId;
    private String mCondition;
    private String mDescr;
    private String mIcon;

    private double mPressure;
    private double mHumidity;

    public long getWeatherId() {
        return mWeatherId;
    }
    public void setWeatherId(long weatherId) {
        mWeatherId = weatherId;
    }
    public String getCondition() {
        return mCondition;
    }
    public void setCondition(String condition) {
        mCondition = condition;
    }
    public String getDescr() {
        return mDescr;
    }
    public void setDescr(String descr) {
        mDescr = descr;
    }
    public String getIcon() {
        return mIcon;
    }
    public void setIcon(String icon) {
        mIcon = icon;
    }
    public double getPressure() {
        return mPressure;
    }
    public void setPressure(double pressure) {
        mPressure = pressure;
    }
    public double getHumidity() {
        return mHumidity;
    }
    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }
}
