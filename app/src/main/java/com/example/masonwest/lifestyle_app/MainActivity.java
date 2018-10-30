package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.LiveData;
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
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements MyRVAdapter.DataPasser, EditUserDetailsFragment.OnDataPass, AppHeaderFragment.OnDataPass {

    private Boolean isEditUser = false;
    private int containerBody;
    private int containerHeader;
    private Boolean userExists = false;
    private UserViewModel mUserViewModel;
//    private LiveData<User> mUser;

    //create an observer that watches the MutableLiveData<User> object
    //possibly not necessary here?
//    final Observer<User> userObserver  = new Observer<User>() {
//        @Override
//        public void onChanged(@Nullable final User user) {
//            // Update the UI if this data variable changes
//            if(user!=null) {
////                String test = "";
////                User temp = mUserViewModel.getUser().getValue();
////                int test2 = 7;
//                //what to do if user changes?
////                mUserViewModel.getUser();
////                Toast.makeText(getActivity(),"User in db changed", Toast.LENGTH_LONG).show();
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // AWS Connection
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();


        setContentView(R.layout.activity_main);
        containerBody = isTablet() ? R.id.fl_frag_edituser_container_tablet : R.id.fl_frag_masterlist_container_phone;
        containerHeader = isTablet() ? R.id.fl_header_tablet : R.id.fl_header_phone;

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

//        mUserViewModel.getUser().observe(this, userObserver);

        if (mUserViewModel.getUser().getValue() == null) {
            User newUser = new User(0);
            mUserViewModel.insert(newUser);
            mUserViewModel.setUser(newUser);
        }
//        User temp = mUserViewModel.getUser().getValue();

        VoidAsyncTask task = mUserViewModel.getNumberOfUserInDatabase();
        task.execute();

        int numUsersInDB = 0;

        try {
            numUsersInDB = (int) task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (numUsersInDB > 0) {
            userExists = true;
        }

        if (savedInstanceState == null /*&& userExists == false*/) {
            //TODO not sure if this is right
            isEditUser = true;
        } else {
            isEditUser = savedInstanceState.getBoolean("editUserBoolean");
        }
        changeDisplay();

       // uploadWithTransferUtility();
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
            case 4: { //Steps Page
                if (isTablet()) {
                    //Create a new detail fragment
                    StepCounterFragment stepFragment = new StepCounterFragment();
                    stepFragment.setArguments(positionBundle);

                    //Replace the detail fragment container
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_itemdetail_container_tablet, stepFragment, "frag_step_tablet");
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
        FragmentManager fManager = getSupportFragmentManager();
        Fragment editUserFragment = fManager.findFragmentByTag("editUserFragment");
        if (editUserFragment != null && editUserFragment.isAdded()) {
            isEditUser = true;
        }
        outState.putBoolean("editUserBoolean", isEditUser);
    }

    public void changeDisplay() {
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTrans = fManager.beginTransaction();

        if (isEditUser) {
            fTrans.replace(containerBody, new EditUserDetailsFragment(), "editUserFragment");
            fTrans.replace(containerHeader, new SignUpHeaderFragment());
        } else {
            fTrans.replace(containerBody, new MasterListFragment(), "masterListFragment");
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
        if (isTablet()) {
            FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fl_frag_itemdetail_container_tablet);
            if (f != null) {
                fTrans.hide(f);
            }
            fTrans.commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fManager = getSupportFragmentManager();
        Fragment editUserFragment = fManager.findFragmentByTag("editUserFragment");
        if (editUserFragment != null && editUserFragment.isAdded()) {
            return;
        } else {
            super.onBackPressed();
        }
    }

}
