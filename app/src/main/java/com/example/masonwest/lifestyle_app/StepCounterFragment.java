package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StepCounterFragment extends Fragment implements SensorEventListener, StepListener {

    //Step counter stuff
    private StepDetector mSimpleStepDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelSensor;
    private int mNumSteps;
    private User mCurrentUser;

    //view model
    private UserViewModel mUserViewModel;

    //xml stuff
    private TextView mTvStepData;
    private Button mBtnStart, mBtnStop;

    public StepCounterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if(user!=null) {
                mCurrentUser = user;
                mTvStepData.setText("" + mCurrentUser.getSteps());
//                mTvFullName.setText(mUserViewModel.getUser().getValue().getFullName());
//                mIvPicture.setImageBitmap(User.calculateProfileImageDataInBitmap(mUserViewModel.getUser().getValue().getProfileImageData()));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_step_counter, container, false);

        //Get the view model
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mUserViewModel.getUser().observe(this, userObserver);

        //Get the views
        mTvStepData = fragmentView.findViewById(R.id.tv_step_data);
//        TvSteps = (TextView) findViewById(R.id.tv_steps);
        mBtnStart = (Button) fragmentView.findViewById(R.id.btn_start);
        mBtnStop = (Button) fragmentView.findViewById(R.id.btn_stop);

        // Get an instance of the SensorManager
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSimpleStepDetector = new StepDetector();
        mSimpleStepDetector.registerListener(StepCounterFragment.this);

        //Turn on the step counter by clicking the button
        mBtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mNumSteps = 0;
                mTvStepData.setText("0");
                mSensorManager.registerListener(StepCounterFragment.this, mAccelSensor, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        mBtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mCurrentUser.setSteps(mNumSteps);
                mUserViewModel.dumpInDB(mCurrentUser);
                mSensorManager.unregisterListener(StepCounterFragment.this);

            }
        });

        return fragmentView;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mSimpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        mNumSteps++;
        mTvStepData.setText("" + mNumSteps);
        mUserViewModel.getUser().getValue().setSteps(mNumSteps);
    }

    /**
     * Allows the page to be lifecycle aware
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Put them in the outgoing Bundle
//        outState.putParcelable("user", currentUser);

        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }
}
