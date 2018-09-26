package com.example.masonwest.lifestyle_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class MainActivity extends AppCompatActivity
        implements MyRVAdapter.DataPasser, EditUserDetailsFragment.OnDataPass, AppHeaderFragment.OnDataPass {

    private MasterListFragment mMasterListFragment;
    private SignUpHeaderFragment mSignUpHeaderFragment;
    private AppHeaderFragment mAppHeaderFragment;
    private Fragment mUserDetailFragment;
    private ArrayList<String> mItemList;
    private User newUser;
    private ArrayList<User> allUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            //CREATE THE VIEW TO ENTER USER INFORMATION
            mUserDetailFragment = new EditUserDetailsFragment();
        } else {
            mUserDetailFragment = getSupportFragmentManager().getFragment(savedInstanceState, "submit_frag");
        }
        //Replace the fragment container
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        if (isTablet()) {
            fTrans.replace(R.id.fl_frag_masterlist_container_tablet, mUserDetailFragment, "submit_frag");
        }
        else {
            fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "submit_frag");
        }

        mSignUpHeaderFragment = new SignUpHeaderFragment();

        //Replace the fragment container
        fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment, "signup_header_frag"); //.getTag()???
        fTrans.commit();

        //Create the list of headers
        mItemList = new ArrayList<>();
        mItemList.add("Fitness Goals >");
        mItemList.add("BMI >");
        mItemList.add("Weather >");
        mItemList.add("Hikes >");
    }

    //This receives the position of the clicked item in the MasterListFragment's RecyclerView
    @Override
    public void passData(int position) {
        Bundle positionBundle = new Bundle();
        positionBundle.putInt("click_position",position);

        //Uses switch statement to tell the passData which fragment to open based on position
        switch(position) {
            case 0: { //Weight Goals Page
                //If we're on a tablet, the fragment occupies the second pane (right). If we're on a phone, the fragment is replaced
                if (isTablet()) {
                    //Create a new detail fragment
                    FitnessGoalsFragment fitnessFragment = new FitnessGoalsFragment();
                    //Replace the detail fragment container
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, fitnessFragment, "frag_fitness");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                } else { //On a phone
                    //Start ItemDetailActivity, pass the string along
                    positionBundle.putString("userFullName", newUser.getName());
                    positionBundle.putInt("userAge", newUser.getAge());
                    positionBundle.putInt("userWeight", newUser.getWeight());
                    positionBundle.putInt("userHeight", newUser.getHeight());
                    positionBundle.putString("userSex", newUser.getSex());
                    positionBundle.putDouble("userGoal", newUser.getGoal());
                    positionBundle.putString("userActivity", newUser.getActivityLevel());

                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 1: { //BMI Page
                if (isTablet()) {
                    //Create a new detail fragment
                    BmiFragment bmiFragment = new BmiFragment();
                    //Replace the detail fragment container
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, bmiFragment, "frag_bmi");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    newUser.calculateBMI();
                    Double bmiValue = newUser.getBMI();
                    positionBundle.putDouble("bmi_data",bmiValue);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 2: { //Weather Page
                if (isTablet()) {
                    //Create a new detail fragment
                    WeatherFragment weatherFragment = new WeatherFragment();
                    //Replace the detail fragment container
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, weatherFragment, "frag_weather");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    String location = newUser.getLocation();
                    positionBundle.putString("location_data",location);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 3: { //Hikes Page
                if (isTablet()) {
                    //Create a new detail fragment
                    HikesFragment hikesFragment = new HikesFragment();
                    //Replace the detail fragment container
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, hikesFragment, "frag_hikes");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
        }
    }

    public boolean isTablet()
    {
        return getResources().getBoolean(R.bool.isTablet);
    }

    //from EditUserFragment
    @Override
    public void onDataPass(String firstName, String lastName, int age, int height, int weight, String city, String country, Bundle thumbnailImage, String sex) {
        // Pull the bitmap image from the bundle
        Bitmap thumbnail = (Bitmap) thumbnailImage.get("data");
        // Create a new user
//        User(int userIDPassed, String firstNamePassed, String lastNamePassed, int agePassed, int heightPassed, float weightPassed, String cityPassed, String countryPassed, Bitmap profilePicPassed, String sexPassed)
        newUser = new User(1, firstName, lastName, age, height, weight, city, country, thumbnail, sex);
        allUsers.add(newUser);
        //MASTER LIST WORK
        //Get the Master List fragment
        mMasterListFragment = new MasterListFragment();

        //Send data to it
        Bundle masterListDataBundle = new Bundle();
        masterListDataBundle.putStringArrayList("item_list",mItemList);
        //Pass data to the fragment
        mMasterListFragment.setArguments(masterListDataBundle);

        //Replace the fragment container
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        // If we're on a tablet, the master fragment appears on the left pane. If we're on a phone,
        // it takes over the whole screen
        if(isTablet()){
            //Pane 1: Master list
            fTrans.replace(R.id.fl_frag_masterlist_container_tablet, mMasterListFragment,"frag_masterlist");
        }
        else{
            fTrans.replace(R.id.fl_frag_masterlist_container_phone, mMasterListFragment, "frag_masterlist");
        }


        //HEADER WORK
        mAppHeaderFragment = new AppHeaderFragment();

        //Get full name
        String fullName = newUser.getName();

        //Send data to it
        Bundle headerBundle = new Bundle();
        headerBundle.putString("userFirstName",firstName);
        headerBundle.putString("userLastName",lastName);
        headerBundle.putString("userFullName",fullName);
        headerBundle.putInt("userAge", age);
        headerBundle.putInt("userWeight", weight);
        headerBundle.putInt("userHeight", height);
        headerBundle.putString("userCity", city);
        headerBundle.putString("userCountry", country);
        headerBundle.putString("userSex", sex);
        headerBundle.putBundle("userPic", thumbnailImage);

        //Pass data to the fragment
        mAppHeaderFragment.setArguments(headerBundle);

        fTrans.replace(R.id.fl_header_phone, mAppHeaderFragment, "app_header_frag");
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    //from App Header
    @Override
    public void onDataPass(String firstName, String lastName, String city, String country, String sex, int age, int weight, int height, Bundle pic) {
        //Replace the fragment container
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "submit_frag"); //.getTag()???

        mSignUpHeaderFragment = new SignUpHeaderFragment();

        //Replace the fragment container
        fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment, "signup_header_frag"); //.getTag()???

        Bundle settingsBundle = new Bundle();
        settingsBundle.putString("userFirstName",firstName);
        settingsBundle.putString("userLastName",lastName);
        settingsBundle.putInt("userAge", age);
        settingsBundle.putInt("userWeight", weight);
        settingsBundle.putInt("userHeight", height);
        settingsBundle.putString("userCity", city);
        settingsBundle.putString("userCountry", country);
        settingsBundle.putString("userSex", sex);
        settingsBundle.putBundle("userPic", pic);
        mUserDetailFragment.setArguments(settingsBundle);
        fTrans.commit();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        FragmentTransaction fTrans2 = getSupportFragmentManager().beginTransaction();
        if (isTablet()) {
            fTrans2.replace(R.id.fl_frag_masterlist_container_tablet, mUserDetailFragment, "submit_frag");
        }
        else {
            fTrans2.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "submit_frag");
        }
        //Replace the fragment container
        fTrans2.replace(R.id.fl_header_phone, mSignUpHeaderFragment, "signup_header_frag"); //.getTag()???
        fTrans2.commit();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mMasterListFragment != null) {
            getSupportFragmentManager().putFragment(outState, "frag_masterlist", mMasterListFragment);
        }
        if(mUserDetailFragment != null) {
            getSupportFragmentManager().putFragment(outState, "submit_frag", mUserDetailFragment);
        }
        if(mSignUpHeaderFragment != null) {
            getSupportFragmentManager().putFragment(outState, "signup_header_frag", mSignUpHeaderFragment);
        }
        if(mAppHeaderFragment != null) {
            getSupportFragmentManager().putFragment(outState, "app_header_frag", mAppHeaderFragment);
        }
    }
}
