package com.example.masonwest.lifestyle_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MyRVAdapter.DataPasser, EditUserDetailsFragment.OnDataPass {

    private MasterListFragment mMasterListFragment;
    private EditUserDetailsFragment mUserDetailFragment;
//    public static final int OPEN_NEW_ACTIVITY = 124;

    private ArrayList<String> mItemList, mItemDetails;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CREATE THE VIEW TO ENTER USER INFORMATION
        mUserDetailFragment = new EditUserDetailsFragment();

        //Replace the fragment container
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "submit_frag");
        fTrans.commit();

        //CREATE THE LIST OF HEADERS...
        mItemList = new ArrayList<>();
        mItemList.add("Weight Tracker");
        mItemList.add("BMI");
        mItemList.add("Weather");
        mItemList.add("Hikes");
        mItemList.add("User Profile");
        mItemDetails = new ArrayList<>();
        mItemDetails.add("Weight Tracker Details");
        mItemDetails.add("BMI Details");
        mItemDetails.add("Weather Details");
        mItemDetails.add("Hikes Details");
        mItemDetails.add("User Profile Details");

    }

    //This receives the position of the clicked item in the MasterListFragment's RecyclerView
    @Override
    public void passData(int position) {
        //Get the string data corresponding to the detail view
        String itemDetailString = mItemDetails.get(position);

        //Put this into a bundle
        Bundle detailBundle = new Bundle();
        detailBundle.putString("item_detail",itemDetailString);

        //If we're on a tablet, the fragment occupies the second pane (right). If we're on a phone,
        //the fragment is
        if(isTablet()) {
            //Create a new detail fragment
            ItemViewDetailFragment detailViewFragment = new ItemViewDetailFragment();

            //Pass data to the fragment
            detailViewFragment.setArguments(detailBundle);

            //Replace the detail fragment container
            FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
            fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, detailViewFragment, "frag_itemdetail");
            fTrans.addToBackStack(null);
            fTrans.commit();
        }
        else{ //On a phone
            //Start ItemDetailActivity, pass the string along
            Intent sendIntent = new Intent(this, ViewDetailActivity.class);
            sendIntent.putExtras(detailBundle);

            startActivity(sendIntent);
        }
    }

    public boolean isTablet()
    {
        return getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public void onDataPass(String firstName, String lastName, int age, Bundle thumbnailImage) {
        // Pull the bitmap image from the bundle
        Bitmap thumbnail = (Bitmap) thumbnailImage.get("data");
        // Create a new user
        //User(int userIDPassed, String firstNamePassed, String lastNamePassed, int agePassed, int heightPassed, float weightPassed, String cityPassed, String countryPassed, Enum sexPassed, Bitmap profilePicPassed) {
        newUser = new User(1, firstName, lastName, age, 72, 160, "Salt Lake", "USA", thumbnail);

        //Get the Master List fragment
        mMasterListFragment = new MasterListFragment();

        //Send data to it
        Bundle sentData = new Bundle();
        sentData.putString("FN_DATA",firstName);
        sentData.putString("LN_DATA",lastName);
        sentData.putBundle("PIC_DATA", thumbnailImage);
        sentData.putStringArrayList("item_list",mItemList);
        //Pass data to the fragment
        mMasterListFragment.setArguments(sentData);

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
        fTrans.addToBackStack(null);
        fTrans.commit();
    }
}
