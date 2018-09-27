package com.example.masonwest.lifestyle_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import static java.lang.Double.NaN;

public class ViewDetailActivity extends AppCompatActivity implements FitnessGoalsFragment.OnDataPass {

    // Fragment member variables
    private FitnessGoalsFragment mFitnessFragment;
    private Fragment mBmiFragment, mWeatherFragment;
//    private WeatherFragment mWeatherFragment;
    private HikesFragment mHikesFragment;
    private String mActivityLevel, mLocation;
    private double mBMR;
    private double mDailyCalories;
    private double mGoal;
    private double mBMIValue;
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

        int position = extras.getInt("click_position");

        switch(position) {
            case 0: {
                //Create the fragment
                if(savedInstanceState == null) {
                    mFitnessFragment = new FitnessGoalsFragment();

                    String firstName = extras.getString("userFirstName");
                    String lastName = extras.getString("userLastName");
                    String activityLevel = extras.getString("userActivityLevel");
                    String sex = extras.getString("userSex");
                    int age = extras.getInt("userAge");
                    int weight = extras.getInt("userWeight");
                    int height = extras.getInt("userHeight");
                    double weightGoal = extras.getDouble("userGoal");

                    Bundle fitnessBundle = new Bundle();
                    fitnessBundle.putString("userFirstName", firstName);
                    fitnessBundle.putString("userLastName", lastName);
                    fitnessBundle.putString("userActivityLevel", activityLevel);
                    fitnessBundle.putString("userSex", sex);
                    fitnessBundle.putInt("userAge", age);
                    fitnessBundle.putInt("userWeight", weight);
                    fitnessBundle.putInt("userHeight", height);
                    fitnessBundle.putDouble("userGoal", weightGoal);
                    //No need to check if we're on a tablet. This activity only gets created on phones.
                    mFitnessFragment.setArguments(fitnessBundle);
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mFitnessFragment, "frag_fitnessdetail");
                    fTrans.commit();
                }
                break;
            }
            case 1: {
                if (savedInstanceState == null) {
                    //Create the fragment
                    mBmiFragment = new BmiFragment();
                    mBMIValue = extras.getDouble("bmi_data");
                } else {
                    mBmiFragment = getSupportFragmentManager().getFragment(savedInstanceState, "frag_BMIdetail");
                    mBMIValue = savedInstanceState.getDouble("userBMI");
                }
                //No need to check if we're on a tablet. This activity only gets created on phones.
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mBmiFragment, "frag_BMIdetail");
                // Make bundle to send to bmi fragment
                Bundle bmiData = new Bundle();
                bmiData.putDouble("bmi_data", mBMIValue);
                mBmiFragment.setArguments(bmiData);
                fTrans.commit();

                break;
            }
            case 2: {
                if (savedInstanceState == null) {
                    //Create the fragment
                    mWeatherFragment = new WeatherFragment();
                    // Get Location from MainActivity
                    mLocation = extras.getString("location_data");
                } else {
//                    mWeatherFragment = getSupportFragmentManager().getFragment(savedInstanceState, "frag_locationdetail");
                    mWeatherFragment = getSupportFragmentManager().getFragment(savedInstanceState, "frag_locationdetail");
                    mLocation = savedInstanceState.getString("userLocation");
                }
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mWeatherFragment, "frag_weatherdetail");
                // Make bundle to send to weather fragment
                Bundle locationData = new Bundle();
                locationData.putString("location_data",mLocation);
                mWeatherFragment.setArguments(locationData);
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
    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        if(mBmiFragment != null) {
            getSupportFragmentManager().putFragment(savedState, "frag_BMIdetail", mBmiFragment);
        }
        if(mWeatherFragment != null) {
            getSupportFragmentManager().putFragment(savedState, "frag_weatherdetail", mWeatherFragment);
        }
        savedState.putDouble("userBMI", mBMIValue);
        savedState.putString("userLocation", mLocation);
    }
}
