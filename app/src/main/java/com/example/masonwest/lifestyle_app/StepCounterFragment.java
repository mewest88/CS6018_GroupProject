package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.SENSOR_SERVICE;

public class StepCounterFragment extends Fragment implements SensorEventListener, StepListener {

    //Step counter stuff
    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;


    private TextView mTvStepData;
    private Button mBtnStart, mBtnStop;
    //    private User currentUser;

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

        //Get the views
        mTvStepData = fragmentView.findViewById(R.id.tv_step_data);
//        TvSteps = (TextView) findViewById(R.id.tv_steps);
        mBtnStart = (Button) fragmentView.findViewById(R.id.btn_start);
        mBtnStop = (Button) fragmentView.findViewById(R.id.btn_stop);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(StepCounterFragment.this);

        mBtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                mTvStepData.setText("0");
                sensorManager.registerListener(StepCounterFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        mBtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(StepCounterFragment.this);

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
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        mTvStepData.setText("" + numSteps);
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
