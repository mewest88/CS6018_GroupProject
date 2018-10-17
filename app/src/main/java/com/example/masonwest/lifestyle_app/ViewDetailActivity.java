package com.example.masonwest.lifestyle_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewDetailActivity extends AppCompatActivity {

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        if(savedInstanceState != null) {
            extras = savedInstanceState.getBundle("extras");
        } else {
            extras = getIntent().getExtras();
        }

        int position = extras.getInt("click_position");

        switch(position) {
            case 0: {
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_itemdetail_container_phone, new FitnessGoalsFragment());
                fTrans.commit();
                break;
            }
            case 1: {
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_itemdetail_container_phone, new BmiFragment());
                fTrans.commit();
                break;
            }
            case 2: {
                //Create the fragment
                Fragment mWeatherFragment = new WeatherFragment();
                // Get Location from MainActivity
                String location = extras.getString("location_data");
                // Make bundle to send to weather fragment
                extras.putString("location_data",location);
                mWeatherFragment.setArguments(extras);
                //No need to check if we're on a tablet. This activity only gets created on phones.
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mWeatherFragment, "frag_weatherdetail");
                fTrans.commit();
                break;
            }
            case 3: {
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_itemdetail_container_phone, new HikesFragment(), "frag_hikesdetail");
                fTrans.commit();
                break;
            }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putBundle("extras", extras);
    }
}
