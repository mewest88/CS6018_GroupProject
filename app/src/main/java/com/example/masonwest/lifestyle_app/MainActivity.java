package com.example.masonwest.lifestyle_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MyRVAdapter.DataPasser, EditUserDetailsFragment.OnDataPass, AppHeaderFragment.HeaderDataPass {

    private Fragment mMasterListFragment, mSignUpHeaderFragment, mAppHeaderFragment, mUserDetailFragment;
    private ArrayList<String> mItemList;
    private User newUser;
    private ArrayList<User> allUsers = new ArrayList<>();
    private Boolean isEditUser = false;

    String mUserActivityLevel, mUserSex, mUserFirstName, mUserLastName, mUserFullName, mUserCity, mUserCountry;
    double mUserBMR, mUserEnteredGoal, mUserDailyRecommendedCalorieIntake;
    int mUserHeight, mUserAge, mUserWeight;
    Bundle mUserProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            //CREATE THE VIEW TO ENTER USER INFORMATION
            mUserDetailFragment = new EditUserDetailsFragment();
            isEditUser = true;
        } else {
            mMasterListFragment = getSupportFragmentManager().getFragment(savedInstanceState, "frag_masterlist");
            mUserDetailFragment = getSupportFragmentManager().getFragment(savedInstanceState, "frag_detail");
            mSignUpHeaderFragment = getSupportFragmentManager().getFragment(savedInstanceState, "signup_header_frag");
            mAppHeaderFragment = getSupportFragmentManager().getFragment(savedInstanceState, "app_header_frag");
            isEditUser = savedInstanceState.getBoolean("editUserBoolean");
        }

        //Replace the fragment container
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        if (isTablet()) {
            fTrans.replace(R.id.fl_frag_edituser_container_tablet, mUserDetailFragment, "submit_frag");
        }
        else {
            fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "frag_userdetail_phone");
        }

        mSignUpHeaderFragment = new SignUpHeaderFragment();

        //Replace the fragment container
        if (isTablet()) {
            fTrans.replace(R.id.fl_header_tablet, mSignUpHeaderFragment, "signup_header_frag"); //.getTag()???
        }
        else {
            fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment, "signup_header_frag"); //.getTag()???
        }

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
        positionBundle.putInt("click_position", position);
        //isEditUser should be false??

        //Uses switch statement to tell the passData which fragment to open based on position
        switch(position) {
            case 0: { //Weight Goals Page
                //If we're on a tablet, the fragment occupies the second pane (right). If we're on a phone, the fragment is replaced
                if (isTablet()) {
                    //Create a new detail fragment
                    FitnessGoalsFragment fitnessFragment = new FitnessGoalsFragment();
                    //Replace the detail fragment container
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, fitnessFragment, "frag_fitness_tablet");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                } else { //On a phone
                    //Start ItemDetailActivity, pass the string along
                    positionBundle.putString("userFullName", mUserFullName);
                    positionBundle.putInt("userAge", mUserAge);
                    positionBundle.putInt("userWeight", mUserWeight);
                    positionBundle.putInt("userHeight", mUserHeight);
                    positionBundle.putString("userSex", mUserSex);

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
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, bmiFragment, "frag_BMI_tablet");
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
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, weatherFragment, "frag_weather_tablet");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    String location = newUser.getLocation();
                    positionBundle.putString("location_data", location);
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
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, hikesFragment, "frag_hikes_tablet");
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
        mUserFirstName = firstName;
        mUserLastName = lastName;
        mUserFullName = firstName + " " + lastName;
        mUserAge = age;
        mUserHeight = height;
        mUserWeight = weight;
        mUserCity = city;
        mUserCountry = country;
        mUserSex = sex;
        mUserProfilePic = thumbnailImage;

        isEditUser = false;
        
        // Hide EditUserData fragment
        showHideFragment(mUserDetailFragment);

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
            fTrans.replace(R.id.fl_frag_masterlist_container_tablet, mMasterListFragment,"frag_masterlist_tablet");
        }
        else{
            fTrans.replace(R.id.fl_frag_masterlist_container_phone, mMasterListFragment, "frag_masterlist_phone");
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

        if(isTablet()){
            //Pane 1: Master list
            fTrans.replace(R.id.fl_header_tablet, mAppHeaderFragment, "app_header_frag");
        }
        else{
            fTrans.replace(R.id.fl_header_phone, mAppHeaderFragment, "app_header_frag");
        }

        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    //from App Header
    @Override

    public void HeaderDataPass(String firstName, String lastName, String city, String country, String sex, int age, int weight, int height, Bundle pic) {
        mUserFirstName = firstName;
        mUserLastName = lastName;
        mUserFullName = firstName + " " + lastName;
        mUserAge = age;
        mUserHeight = height;
        mUserWeight = weight;
        mUserCity = city;
        mUserCountry = country;
        mUserSex = sex;
        mUserProfilePic = pic;

        isEditUser = true;

        //Replace the fragment container
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        if (isTablet()) {
            fTrans.replace(R.id.fl_frag_edituser_container_tablet, mUserDetailFragment, "submit_frag");
        }
        else {
            fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "frag_userdetail_phone");
        }

        mSignUpHeaderFragment = new SignUpHeaderFragment();

        //Replace the fragment container
        if (isTablet()) {
            fTrans.replace(R.id.fl_header_tablet, mSignUpHeaderFragment, "frag_signupheader_tablet"); //.getTag()???
        }
        else {
            fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment, "frag_signupheader_phone"); //.getTag()???
        }

        Bundle settingsBundle = new Bundle();
        settingsBundle.putString("userFirstName", firstName);
        settingsBundle.putString("userLastName", lastName);
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

    // Call this function inside onClick of button
    public void showHideFragment(final Fragment fragment){
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);

        if (fragment.isHidden()) {
            fragTransaction.show(fragment);
            Log.d("hidden","Show");
        } else {
            fragTransaction.hide(fragment);
            Log.d("Shown","Hide");
        }

        fragTransaction.commit();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        FragmentManager fMan = getSupportFragmentManager();
        FragmentTransaction fTrans = fMan.beginTransaction();


        if(isEditUser) {
            if(isTablet()) {
                fTrans.replace(R.id.fl_frag_edituser_container_tablet, mUserDetailFragment);
            } else {
                fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment);
            }
            fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment);
        } else {
            if(isTablet()) {
                fTrans.replace(R.id.fl_frag_masterlist_container_tablet, mMasterListFragment);
            } else {
                fTrans.replace(R.id.fl_frag_masterlist_container_phone, mMasterListFragment);
            }
            fTrans.replace(R.id.fl_header_phone, mAppHeaderFragment);
        }
        fTrans.commit();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("editUserBoolean", isEditUser);
        super.onSaveInstanceState(outState);
        outState.putInt("userAge", mUserAge);
        outState.putInt("userHeight", mUserHeight);
        outState.putInt("userWeight", mUserWeight);
        outState.putString("userSex", mUserSex);
        outState.putString("userFirstName", mUserFirstName);
        outState.putString("userLastName", mUserLastName);
        outState.putString("userFullName", mUserFullName);
        outState.putBundle("userPic", mUserProfilePic);

        if(mMasterListFragment != null && mMasterListFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "frag_masterlist", mMasterListFragment);
        }
//        if(mUserDetailFragment != null && mUserDetailFragment.isAdded()) {
//            getSupportFragmentManager().putFragment(outState, "frag_detail", mUserDetailFragment);
//        }
        getSupportFragmentManager().putFragment(outState, "frag_detail", mUserDetailFragment);
        if(mSignUpHeaderFragment != null && mSignUpHeaderFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "signup_header_frag", mSignUpHeaderFragment);
        }
        if(mAppHeaderFragment != null && mAppHeaderFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "app_header_frag", mAppHeaderFragment);
        }
    }
}
