package com.example.masonwest.lifestyle_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MyRVAdapter.DataPasser, EditUserDetailsFragment.OnDataPass, AppHeaderFragment.HeaderDataPass {

    private Fragment mMasterListFragment, mSignUpHeaderFragment, mAppHeaderFragment, mUserDetailFragment;
    private ArrayList<String> mItemList;
    private User currentUser;
    private ArrayList<User> allUsers = new ArrayList<>();
    private Boolean isEditUser = false;

//    Bundle userData;
//    String mUserSex, mUserFirstName, mUserLastName, mUserFullName, mUserCity, mUserCountry;
//    int mUserHeight, mUserAge, mUserWeight;
//    Bitmap mUserProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the list of headers
        mItemList = new ArrayList<>();
        mItemList.add("Fitness Goals >");
        mItemList.add("BMI >");
        mItemList.add("Weather >");
        mItemList.add("Hikes >");

        if(savedInstanceState == null) {
            //CREATE THE VIEW TO ENTER USER INFORMATION
            mUserDetailFragment = new EditUserDetailsFragment();
            isEditUser = true;
        } else {
//            mUserSex= savedInstanceState.getString("userSex");
            mMasterListFragment = getSupportFragmentManager().getFragment(savedInstanceState, "frag_masterlist");
            mUserDetailFragment = getSupportFragmentManager().getFragment(savedInstanceState, "frag_detail");
            mSignUpHeaderFragment = getSupportFragmentManager().getFragment(savedInstanceState, "signup_header_frag");
            mAppHeaderFragment = getSupportFragmentManager().getFragment(savedInstanceState, "app_header_frag");
            currentUser = savedInstanceState.getParcelable("user");
        }

        if(isEditUser) {
            //Replace the fragment container
            FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

            if (isTablet()) {
                fTrans.replace(R.id.fl_frag_edituser_container_tablet, mUserDetailFragment, "frag_detail");
            }
            else {
                fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "frag_detail");
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
        }
    }

    //This receives the position of the clicked item in the MasterListFragment's RecyclerView
    @Override
    public void passData(int position) {
        Bundle positionBundle = new Bundle();
        positionBundle.putInt("click_position", position);
//        positionBundle.putBundle("userData", userData);
        positionBundle.putParcelable("user", currentUser);

        //Uses switch statement to tell the passData which fragment to open based on position
        switch(position) {
            case 0: { //Weight Goals Page
                //If we're on a tablet, the fragment occupies the second pane (right). If we're on a phone, the fragment is replaced
                if (isTablet()) {
                    //Create a new detail fragment
                    FitnessGoalsFragment fitnessFragment = new FitnessGoalsFragment();
                    fitnessFragment.setArguments(positionBundle);

                    //Replace the detail fragment container
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, fitnessFragment, "frag_fitness_tablet");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                    break;
                } else { //On a phone
                    //Start ItemDetailActivity, pass the bundle
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
                    bmiFragment.setArguments(positionBundle);

                    //Replace the detail fragment container
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, bmiFragment, "frag_BMI_tablet");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                    break;
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 2: { //Weather Page
                if (isTablet()) {
                    //Create a new detail fragment
                    WeatherFragment weatherFragment = new WeatherFragment();
                    weatherFragment.setArguments(positionBundle);

                    //Replace the detail fragment container
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, weatherFragment, "frag_weather_tablet");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                    break;
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 3: { //Hikes Page
                if (isTablet()) {
                    //Create a new detail fragment
                    HikesFragment hikesFragment = new HikesFragment();
                    hikesFragment.setArguments(positionBundle);

                    //Replace the detail fragment container
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, hikesFragment, "frag_hikes_tablet");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                    break;
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
    public void onDataPass(User existingUser) {
        currentUser = existingUser;

        // Hide EditUserData fragment
        showHideFragment(mUserDetailFragment);

        // Create a new user
        // Should we always create a new user?
        allUsers.add(currentUser);

        //MASTER LIST WORK
        //Get the Master List fragment
        mMasterListFragment = new MasterListFragment();

        //Send data to it
        Bundle masterListDataBundle = new Bundle();
        masterListDataBundle.putStringArrayList("itemList", mItemList);
        masterListDataBundle.putParcelable("user", currentUser);

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

        //Pass data to the fragment
        mAppHeaderFragment.setArguments(masterListDataBundle);

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

    public void HeaderDataPass(User existingUser) {
//        userData = headerData;
        Bundle headerBundle = new Bundle();
        currentUser = existingUser;
        isEditUser = true;
        headerBundle.putParcelable("user", currentUser);

        //Replace the fragment container
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        if (isTablet()) {
            fTrans.replace(R.id.fl_frag_edituser_container_tablet, mUserDetailFragment, "frag_detail");
        }
        else {
            fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "frag_detail");
        }
        showHideFragment(mUserDetailFragment);
        showHideFragment(mMasterListFragment);
        mSignUpHeaderFragment = new SignUpHeaderFragment();

        //Replace the fragment container
        if (isTablet()) {
            fTrans.replace(R.id.fl_header_tablet, mSignUpHeaderFragment, "frag_signupheader_tablet"); //.getTag()???
        }
        else {
            fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment, "frag_signupheader_phone"); //.getTag()???
        }

        mUserDetailFragment.setArguments(headerBundle);
        fTrans.commit();
    }

    // Call this function inside onClick of button
    public void showHideFragment(final Fragment fragment){
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
//        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
//                android.R.animator.fade_out);

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
//        userData = savedInstanceState.getBundle("userData");
        currentUser = savedInstanceState.getParcelable("user");
        isEditUser = savedInstanceState.getBoolean("editUserBoolean");
        FragmentManager fMan = getSupportFragmentManager();
        FragmentTransaction fTrans = fMan.beginTransaction();

        if(isEditUser) {
            if(isTablet()) {
                fTrans.replace(R.id.fl_frag_edituser_container_tablet, mUserDetailFragment);
                fTrans.replace(R.id.fl_header_tablet, mSignUpHeaderFragment);
            } else {
                fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment);
                fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment);
            }
        } else {
            if(isTablet()) {
                fTrans.replace(R.id.fl_frag_masterlist_container_tablet, mMasterListFragment);
                fTrans.replace(R.id.fl_header_tablet, mAppHeaderFragment);
            } else {
                fTrans.replace(R.id.fl_frag_masterlist_container_phone, mMasterListFragment);
                fTrans.replace(R.id.fl_header_phone, mAppHeaderFragment);
            }
        }
        fTrans.commit();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("editUserBoolean", isEditUser);
//        outState.putBundle("userData", userData);
        outState.putParcelable("user", currentUser);

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
    @Override
    public void onBackPressed() {

        isEditUser = true;
        if (isTablet()) {
            super.onBackPressed();
            showHideFragment(mUserDetailFragment);
            showHideFragment(mMasterListFragment);
        } else {
            FragmentManager fMan = getSupportFragmentManager();
            FragmentTransaction fTrans = fMan.beginTransaction();
            fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment);
            fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment);
            fTrans.commit();
            showHideFragment(mUserDetailFragment);
        }
    }
}
