package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements MyRVAdapter.DataPasser, EditUserDetailsFragment.OnDataPass, AppHeaderFragment.OnDataPass {

    private Boolean isEditUser = false;
    private int container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = isTablet() ? R.id.fl_frag_edituser_container_tablet : R.id.fl_frag_masterlist_container_phone;

        if (savedInstanceState == null) {
            //TODO not sure if this is right
            isEditUser = true;
        } else {
            isEditUser = savedInstanceState.getBoolean("editUserBoolean");
        }
        changeDisplay();
    }

    //This receives the position of the clicked item in the MasterListFragment's RecyclerView
    @Override
    public void passData(int position) {
        Bundle positionBundle = new Bundle();
        positionBundle.putInt("click_position", position);
        //Uses switch statement to tell the passData which fragment to open based on position
        switch (position) {
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

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    // Call this function inside onClick of button
//    public void showHideFragment(final Fragment fragment){
//        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
////        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
////                android.R.animator.fade_out);
//
//        if (fragment.isHidden()) {
//            fragTransaction.show(fragment);
//            Log.d("hidden","Show");
//        } else {
//            fragTransaction.hide(fragment);
//            Log.d("Shown","Hide");
//        }
//        fragTransaction.commit();
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("editUserBoolean", isEditUser);
    }

    //    @Override
//    public void onBackPressed() {
//
//        isEditUser = true;
//        if (isTablet()) {
//            super.onBackPressed();
//            showHideFragment(mUserDetailFragment);
//            showHideFragment(mMasterListFragment);
//        } else {
//            FragmentManager fMan = getSupportFragmentManager();
//            FragmentTransaction fTrans = fMan.beginTransaction();
//            fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment);
//            fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment);
//            fTrans.commit();
//            showHideFragment(mUserDetailFragment);
//        }
//    }

    public void changeDisplay() {
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (isEditUser) {
            fTrans.replace(container, new EditUserDetailsFragment(), "editUserFragment");
            fTrans.replace(R.id.fl_header_phone, new SignUpHeaderFragment(), "editUserHeaderFragment");
        } else {
            fTrans.replace(container, new MasterListFragment(), "test");
            fTrans.replace(R.id.fl_header_phone, new AppHeaderFragment(), "test2");
        }
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    @Override
    public void onEditUserSubmit() {
        isEditUser = false;
        changeDisplay();
    }

    @Override
    public void onSettingsButtonClick() {
        isEditUser = true;
        changeDisplay();
    }
}
