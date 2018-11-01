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

        if (mUserViewModel.getUser().getValue() == null) {
            User newUser = new User(0);
            // Adds the new user to the database
            mUserViewModel.insert(newUser);
            // Adds the new user to the live data in the repository
            mUserViewModel.setUser(newUser);
        }

        VoidAsyncTask task = mUserViewModel.getNumberOfUserInDatabase();
        task.execute();

        int numUsersInDB = 0;
        /* Counts the number of users saved in the database. This will be used to allow switching
           between users in the future */
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
        if (savedInstanceState == null) {
            isEditUser = true;
        } else {
            isEditUser = savedInstanceState.getBoolean("editUserBoolean");
        }

        changeDisplay();
        uploadWithTransferUtility();
    }

    /**
     * This receives the position of the clicked item in the MasterListFragment's RecyclerView
     * This Creates the new fragment for the new view the user expects to see
     * @param position
     */
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

    public File getDatabaseFile() {
        String backupDBPath = UserDatabase.getDatabase(this).getOpenHelper().getWritableDatabase().getPath();
        File dbPath = new File(backupDBPath) ;
        if (dbPath.exists()) {
            Log.d("UserRepository", "File created successfully.") ;
        }
        return dbPath ;
    }

        public void uploadWithTransferUtility() {
            String KEY = "AKIAJ3FQCHRW5PELY2RA";
            String SECRET = "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";

            BasicAWSCredentials credentials = new BasicAWSCredentials(KEY, SECRET);
            AmazonS3Client client = new AmazonS3Client(credentials);

            TransferUtility transferUtility =
                    TransferUtility.builder()
                             .context(getApplicationContext())
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(client)
                            .build();

            File databaseFile = getDatabaseFile();

            TransferObserver uploadObserver =
                    transferUtility.upload(
                            databaseFile.getName(), databaseFile);

            // Attach a listener to the observer to get state update and progress notifications
            uploadObserver.setTransferListener(new TransferListener() {

                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        Log.d("YourMainActivity", "Uploaded a file!");

                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;

                    Log.d("YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent
                            + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
                }

                @Override
                public void onError(int id, Exception ex) {
                    // Handle errors
                }

            });

            // If you prefer to poll for the data, instead of attaching a
            // listener, check for the state and progress in the observer.
            if (TransferState.COMPLETED == uploadObserver.getState()) {
                Log.d("YourMainActivity", "Uploaded a file!");
            }

            //Log.d("YourMainActivity", "Bytes Transferred: " + uploadObserver.getBytesTransferred());
            //Log.d("YourMainActivity", "Bytes Total: " + uploadObserver.getBytesTotal());

        }

        private void downloadWithTransferUtility () {

            TransferUtility transferUtility =
                    TransferUtility.builder()
                            //.context(getApplicationContext())
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                            .build();

            File databaseFile = getDatabaseFile();

            TransferObserver downloadObserver =
                    transferUtility.download(
                            "s3Folder/s3Key.txt",
                            databaseFile);

            // Attach a listener to the observer to get state update and progress notifications
            downloadObserver.setTransferListener(new TransferListener() {

                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        // Handle a completed upload.
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;

                    Log.d("YourActivity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
                }

                @Override
                public void onError(int id, Exception ex) {
                    // Handle errors
                }

            });

            // If you prefer to poll for the data, instead of attaching a
            // listener, check for the state and progress in the observer.
            if (TransferState.COMPLETED == downloadObserver.getState()) {
                // Handle a completed upload.
            }

            Log.d("YourActivity", "Bytes Transferrred: " + downloadObserver.getBytesTransferred());
            Log.d("YourActivity", "Bytes Total: " + downloadObserver.getBytesTotal());
        }
}
