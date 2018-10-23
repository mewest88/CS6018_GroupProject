//package com.example.masonwest.lifestyle_app;
//
//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModelProviders;
//import android.content.Context;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import static android.content.Context.SENSOR_SERVICE;
//
//public class StepCounterFragment extends Fragment {
//
//    //Step counter stuff
////    private StepDetector simpleStepDetector;
//    private SensorManager mSensorManager;
//    private Sensor countSensor;
//    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
//    private int numSteps;
//
//    private boolean isRunning = false;
//
//    private TextView mTvStepData;
//    private Button mBtnStart, mBtnStop;
//    //    private User currentUser;
//
//    public StepCounterFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View fragmentView = inflater.inflate(R.layout.fragment_step_counter, container, false);
//
//        //Get the views
//        mTvStepData = fragmentView.findViewById(R.id.tv_step_data);
////        TvSteps = (TextView) findViewById(R.id.tv_steps);
//        mBtnStart = (Button) fragmentView.findViewById(R.id.btn_start);
//        mBtnStop = (Button) fragmentView.findViewById(R.id.btn_stop);
//
//        // Get an instance of the mSensorManager
//        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
//        countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
////        simpleStepDetector = new StepDetector();
////        simpleStepDetector.registerListener(this);
//
////        mBtnStart.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View arg0) {
////
////                numSteps = 0;
////                mTvStepData.setText("0");
////                isRunning = true;
//////                if(countSensor != null) {
////                    mSensorManager.registerListener(StepCounterFragment.this, countSensor, SensorManager.SENSOR_DELAY_FASTEST);
//////                } else {
//////                    Toast.makeText(getActivity(), "Sensor not found", Toast.LENGTH_SHORT).show();
//////                }
////
////            }
////        });
////
////
////        mBtnStop.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View arg0) {
////
////                isRunning = false;
////                mSensorManager.unregisterListener(StepCounterFragment.this);
////
////            }
////        });
//
//        return fragmentView;
//    }
//
//    private SensorEventListener mListener = new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent sensorEvent) {
//            mTvStepData.setText("" + String.valueOf(sensorEvent.values[0]));
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int i) {
//
//        }
//    };
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if(countSensor!=null){
//            mSensorManager.registerListener(mListener, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if(countSensor!=null){
//            mSensorManager.unregisterListener(mListener);
//        }
//    }
//
////    @Override
////    public void onAccuracyChanged(Sensor sensor, int accuracy) {
////    }
////
////    @Override
////    public void onSensorChanged(SensorEvent event) {
////        if(isRunning) {
////            mTvStepData.setText(String.valueOf(event.values[0]));
////        }
////    }
//
////    @Override
////    public void step(long timeNs) {
////        numSteps++;
////        mTvStepData.setText(TEXT_NUM_STEPS + numSteps);
////    }
//
//    /**
//     * Allows the page to be lifecycle aware
//     * @param outState
//     */
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        //Put them in the outgoing Bundle
////        outState.putParcelable("user", currentUser);
//
//        //Save the view hierarchy
//        super.onSaveInstanceState(outState);
//    }
//}