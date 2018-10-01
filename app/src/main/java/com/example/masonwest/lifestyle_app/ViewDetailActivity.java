package com.example.masonwest.lifestyle_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewDetailActivity extends AppCompatActivity implements FitnessGoalsFragment.OnDataPass {

    // Fragment member variables
    private FitnessGoalsFragment mFitnessFragment;
    private Fragment mBmiFragment;
    private WeatherFragment mWeatherFragment;
    private HikesFragment mHikesFragment;
    private Bundle extras;
//    private String mActivityLevel;
//    private double mBMR;
//    private double mDailyCalories;
//    private double mGoal;
//    private double mBMIValue;
    private User currentUser;

    @Override
    public void onDataPass(User currentUserPassed) {
        currentUser = currentUserPassed;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        if(savedInstanceState != null) {
            currentUser = savedInstanceState.getParcelable("user");
            extras = savedInstanceState.getBundle("extras");
        } else {
            extras = getIntent().getExtras();
            currentUser = extras.getParcelable("user");
            extras.putParcelable("user", currentUser);
        }

        int position = extras.getInt("click_position");

        switch(position) {
            case 0: {
                //Create the fragment
                if(savedInstanceState == null) {
                    mFitnessFragment = new FitnessGoalsFragment();
//                    String firstName = extras.getString("userFirstName");
//                    String lastName = extras.getString("userLastName");
//                    String activityLevel = extras.getString("userActivityLevel");
//                    String sex = extras.getString("userSex");
//                    int age = extras.getInt("userAge");
//                    int weight = extras.getInt("userWeight");
//                    int height = extras.getInt("userHeight");
//                    double weightGoal = extras.getDouble("userGoal");
//
//                    Bundle fitnessBundle = new Bundle();
//                    fitnessBundle.putString("userFirstName", firstName);
//                    fitnessBundle.putString("userLastName", lastName);
//                    fitnessBundle.putString("userActivityLevel", activityLevel);
//                    fitnessBundle.putString("userSex", sex);
//                    fitnessBundle.putInt("userAge", age);
//                    fitnessBundle.putInt("userWeight", weight);
//                    fitnessBundle.putInt("userHeight", height);
//                    fitnessBundle.putDouble("userGoal", weightGoal);
                    //No need to check if we're on a tablet. This activity only gets created on phones.
                    mFitnessFragment.setArguments(extras);
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
//                    mBMIValue = extras.getDouble("bmi_data");
                } else {
                    mBmiFragment = getSupportFragmentManager().getFragment(savedInstanceState, "frag_BMIdetail");
//                    mBMIValue = savedInstanceState.getDouble("userBMI");
                }
                    //No need to check if we're on a tablet. This activity only gets created on phones.
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mBmiFragment, "frag_BMIdetail");
                    // Make bundle to send to bmi fragment
//                    Bundle userData = new Bundle();
//                    bmiData.putDouble("bmi_data", mBMIValue);

                    mBmiFragment.setArguments(extras);
                    fTrans.commit();

                break;
            }
            case 2: {
                //Create the fragment
                mWeatherFragment = new WeatherFragment();
                // Get Location from MainActivity
                String location = extras.getString("location_data");
                // Make bundle to send to weather fragment
//                Bundle locationData = new Bundle();
                extras.putString("location_data",location);
                mWeatherFragment.setArguments(extras);
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
    }
    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        if(mBmiFragment != null) {
            getSupportFragmentManager().putFragment(savedState, "frag_BMIdetail", mBmiFragment);
        }
        savedState.putParcelable("user", currentUser);
        savedState.putBundle("extras", extras);
    }
}
