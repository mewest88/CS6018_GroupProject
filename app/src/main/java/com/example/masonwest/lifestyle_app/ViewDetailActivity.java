package com.example.masonwest.lifestyle_app;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class ViewDetailActivity extends AppCompatActivity {

    // Fragment member variables
    private FitnessGoalsFragment mFitnessFragment;
    private BmiFragment mBmiFragment;
    private WeatherFragment mWeatherFragment;
    private HikesFragment mHikesFragment;

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
                //No need to check if we're on a tablet. This activity only gets created on phones.
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
