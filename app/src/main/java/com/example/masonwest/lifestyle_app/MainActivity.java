package com.example.masonwest.lifestyle_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
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
        implements MyRVAdapter.DataPasser, EditUserDetailsFragment.OnDataPass {

    private MasterListFragment mMasterListFragment;
    private SignUpHeaderFragment mSignUpHeaderFragment;
    private AppHeaderFragment mAppHeaderFragment;
    private EditUserDetailsFragment mUserDetailFragment;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String activityLevel = intent.getStringExtra("activityLevel");
            double BMR = intent.getDoubleExtra("BMR", 0);
            double dailyCalories = intent.getDoubleExtra("dailyCalories", 0);
            double goal = intent.getDoubleExtra("goal", 0);
            newUser.updateActivityLevel(activityLevel);
            newUser.updateBMR(BMR);
            newUser.updateDailyRecommendedCalorieIntake(dailyCalories);
            newUser.updateWeeklyGainLoss(goal);
            Log.d("receiver", "Got message: " + activityLevel);
        }
    };
//    public static final int OPEN_NEW_ACTIVITY = 124;

    private ArrayList<String> mItemList, mItemDetails;
    private User newUser;
    private ArrayList<User> allUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CREATE THE VIEW TO ENTER USER INFORMATION
        mUserDetailFragment = new EditUserDetailsFragment();

        //Replace the fragment container
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "submit_frag"); //.getTag()???
//        fTrans.commit();

        mSignUpHeaderFragment = new SignUpHeaderFragment();

        //Replace the fragment container
        fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment, "header_frag"); //.getTag()???
        fTrans.commit();

        //Create the list of headers
        mItemList = new ArrayList<>();
        mItemList.add("Weight Tracker");
        mItemList.add("BMI");
        mItemList.add("Weather");
        mItemList.add("Hikes");

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("updateFitnessGoals"));
    }

    //This receives the position of the clicked item in the MasterListFragment's RecyclerView
    @Override
    public void passData(int position) {
//        //Get the string data corresponding to the detail view
//        String itemDetailString = mItemDetails.get(position);

//        //Put this into a bundle
//        Bundle detailBundle = new Bundle();
//        detailBundle.putString("item_detail",itemDetailString);

        Bundle positionBundle = new Bundle();
        positionBundle.putInt("click_position",position);

        //AT THIS POINT - i have the position, so I need to have a switch statement to tell the passData which fragment to open


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
//            fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "frag_masterlist");
        }
//        fTrans.addToBackStack(null);
//        fTrans.commit();

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

        fTrans.replace(R.id.fl_header_phone, mAppHeaderFragment, "header_frag");
        fTrans.addToBackStack(null);
        fTrans.commit();
    }
    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
