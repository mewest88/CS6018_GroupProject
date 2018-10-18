package com.example.masonwest.lifestyle_app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements MyRVAdapter.DataPasser, EditUserDetailsFragment.OnDataPass, AppHeaderFragment.OnDataPass {

    private Boolean isEditUser = false;
    private int containerEditUser;
    private int containerMasterList;
    private int containerHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        containerMasterList = isTablet() ? R.id.fl_frag_masterlist_container_tablet : R.id.fl_frag_masterlist_container_phone;
        containerEditUser = isTablet() ? R.id.fl_frag_edituser_container_tablet : R.id.fl_frag_masterlist_container_phone;
        containerHeader = isTablet() ? R.id.fl_header_tablet : R.id.fl_header_phone;

        if (savedInstanceState == null) {
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("editUserBoolean", isEditUser);
    }

    public void changeDisplay() {
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if (isEditUser) {
            fTrans.replace(containerEditUser, new EditUserDetailsFragment());
            fTrans.replace(containerHeader, new SignUpHeaderFragment());
        } else {
            fTrans.hide(getSupportFragmentManager().findFragmentById(containerEditUser));
            fTrans.replace(containerMasterList, new MasterListFragment());
            fTrans.replace(containerHeader, new AppHeaderFragment());
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
        if(isTablet()) {
            FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
            Fragment itemDetailFragment = getSupportFragmentManager().findFragmentById(R.id.fl_frag_itemdetail_container_tablet);
            Fragment masterListFragment = getSupportFragmentManager().findFragmentById(containerMasterList);
            if (itemDetailFragment != null) {
                fTrans.hide(itemDetailFragment);
            }
            if (masterListFragment != null) {
                fTrans.hide(masterListFragment);
            }
            fTrans.commit();
        }
    }
    @Override
    public void onBackPressed() {
        if(!isEditUser) {
           isEditUser = !isEditUser;
        }
        super.onBackPressed();
    }
}
