package com.example.masonwest.lifestyle_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BmiFragment extends Fragment {

    private TextView mTvBMIData;
    private Double bmiValue;
    private String mStringBMIData, bmiValueString, bmiData;

    public BmiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_bmi, container, false);

        //Get the views
        mTvBMIData = (TextView) fragmentView.findViewById(R.id.tv_bmi_data);

        if (savedInstanceState != null) {
            bmiValueString = savedInstanceState.getString("BMI_TEXT");
        }
        else {
            //Get the bmi double to display
            if(getArguments() == null) {

            }
            bmiValue = getArguments().getDouble("bmi_data");
            bmiValueString = Double.toString(bmiValue);
        }

        //Set the text in the fragment
        mTvBMIData.setText("" + bmiValueString);

        return fragmentView;
    }

    /**
     * Allows the page to be lifecycle aware
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Get the strings
        mStringBMIData = mTvBMIData.getText().toString();

        //Put them in the outgoing Bundle
        outState.putString("BMI_TEXT",mStringBMIData);

        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }
}