package com.example.masonwest.lifestyle_app;

// Used this amazing blog to build step detector
// http://www.gadgetsaint.com/android/create-pedometer-step-counter-android/#.W832EVJMHOQ

/**
 * Will listen to the step alerts
 */
public interface StepListener {

    public void step(long timeNs);

}
