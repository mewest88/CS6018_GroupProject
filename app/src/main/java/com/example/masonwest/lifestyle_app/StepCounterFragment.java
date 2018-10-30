package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
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
    private Sensor mLinearAccelerometer;
    public MediaPlayer mPlayer;
    private static final int SHAKE_THRESHOLD = 5;
    double last_x;
    double last_y;
    double last_z;
    long lastTime;

    //view model
    private UserViewModel mUserViewModel;

    //xml stuff
    private TextView mTvStepData;

    public StepCounterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_step_counter, container, false);

        mPlayer = new MediaPlayer().create(getActivity(), Settings.System.DEFAULT_RINGTONE_URI);

        //Get the view model
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        //Get the views
        mTvStepData = fragmentView.findViewById(R.id.tv_step_data);

        // Get an instance of the SensorManager
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSimpleStepDetector = new StepDetector();
        mSimpleStepDetector.registerListener(StepCounterFragment.this);
        mLinearAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        return fragmentView;
    }

    private SensorEventListener mShakeListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            //Get the acceleration rates along the y and z axes
            double now_x = sensorEvent.values[0];
            double now_y = sensorEvent.values[1];
            double now_z = sensorEvent.values[2];
            long currentTime = System.currentTimeMillis();
            long dTime = currentTime - lastTime;

            if (dTime > 100) {
                lastTime = currentTime;
                double startShake = Math.abs(last_x - now_x);
                double stopShake = Math.abs(last_y - now_y);

                if (startShake > SHAKE_THRESHOLD) {
                    if(!mPlayer.isPlaying()) {
                        mPlayer.start();
                    }
                    mSensorManager.registerListener(StepCounterFragment.this, mAccelSensor, SensorManager.SENSOR_DELAY_FASTEST);
                } else if (stopShake > SHAKE_THRESHOLD) {
                    mPlayer.stop();
                    mSensorManager.unregisterListener(StepCounterFragment.this);
                }
            }
            last_x = now_x;
            last_y = now_y;
            last_z = now_z;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

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

    @Override
    public void onResume() {
        super.onResume();
        if(mLinearAccelerometer!=null){
            mSensorManager.registerListener(mShakeListener, mLinearAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mLinearAccelerometer!=null){
            mSensorManager.unregisterListener(mShakeListener);
        }
    }
}
