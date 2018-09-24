package com.example.masonwest.lifestyle_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

public class ViewDetailActivity extends AppCompatActivity implements FitnessGoalsFragment.OnDataPass {

    // Fragment member variables
    private FitnessGoalsFragment mFitnessFragment;
    private BmiFragment mBmiFragment;
    private WeatherFragment mWeatherFragment;
    private HikesFragment mHikesFragment;
    private String mActivityLevel;
    private double mBMR;
    private double mDailyCalories;
    private double mGoal;
    @Override
    public void onDataPass(String activityLevel, double BMR, double dailyCalories, double goal) {
        mActivityLevel = activityLevel;
        mBMR = BMR;
        mDailyCalories = dailyCalories;
        mGoal = goal;
//        Intent toMain = new Intent(this, MainActivity.class);
//        Intent toMain = new Intent("updateFitnessGoals");
//        toMain.putExtra("activityLevel", mActivityLevel);
//        toMain.putExtra("BMR", mBMR);
//        toMain.putExtra("dailyCalories", mDailyCalories);
//        toMain.putExtra("goal", mGoal);
//
//        Log.d("sender", "Broadcasting message");
//        LocalBroadcastManager.getInstance(this).sendBroadcast(toMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        //Pass data to the fragment
        Bundle extras = getIntent().getExtras();
//        mItemDetailFragment.setArguments();
//        Object objectValue = extras.get("key");
//        Class classValue = objectValue.getClass();
        int position = extras.getInt("click_position");

        switch(position) {
            case 0: {
                //Create the fragment
                mFitnessFragment = new FitnessGoalsFragment();
                String firstName = extras.getString("userFirstName");
                String lastName = extras.getString("userLastName");
                String activityLevel = extras.getString("userActivityLevel");
                int age = extras.getInt("userAge");
                int weight = extras.getInt("userWeight");
                int height = extras.getInt("userHeight");
                double weightGoal = extras.getDouble("userGoal");

                Bundle fitnessBundle = new Bundle();
                fitnessBundle.putString("userFirstName", firstName);
                fitnessBundle.putString("userLastName", lastName);
                fitnessBundle.putString("userActivityLevel", activityLevel);
                fitnessBundle.putInt("userAge", age);
                fitnessBundle.putInt("userWeight", weight);
                fitnessBundle.putInt("userHeight", height);
                fitnessBundle.putDouble("userGoal", weightGoal);
                //No need to check if we're on a tablet. This activity only gets created on phones.
                mFitnessFragment.setArguments(fitnessBundle);
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mFitnessFragment, "frag_fitnessdetail");
                fTrans.commit();
                break;
            }
            case 1: {
                //Create the fragment
                mBmiFragment = new BmiFragment();
                // Get BMI from MainActivity
                Double bmiValue = extras.getDouble("bmi_data");
                // Make bundle to send to bmi fragment
                Bundle bmiData = new Bundle();
                bmiData.putDouble("bmi_data",bmiValue);
                mBmiFragment.setArguments(bmiData);
                //No need to check if we're on a tablet. This activity only gets created on phones.
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mBmiFragment, "frag_BMIdetail");
                fTrans.commit();
                break;
            }
            case 2: {
                //Create the fragment
                mWeatherFragment = new WeatherFragment();
                //No need to check if we're on a tablet. This activity only gets created on phones.
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mWeatherFragment, "frag_weatherdetail");
                fTrans.commit();
                break;
            }
            case 3: {
                //Create the fragment
                mHikesFragment = new HikesFragment();
                //No need to check if we're on a tablet. This activity only gets created on phones.
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mHikesFragment, "frag_hikesdetail");
                fTrans.commit();
                break;
            }
        }

//        //Create the fragment
//        mItemDetailFragment = new ItemViewDetailFragment();
//
//        //No need to check if we're on a tablet. This activity only gets created on phones.
//        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
//        fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mItemDetailFragment, "frag_itemdetail");
//        fTrans.commit();
    }

}
