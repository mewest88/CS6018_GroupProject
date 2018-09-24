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

    public BmiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    //TODO: make lifecycle aware - use ass2 part1 as reference
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_bmi, container, false);

        //Get the views
        mTvBMIData = (TextView) fragmentView.findViewById(R.id.tv_bmi_data);

        //Get the bmi double to display
        bmiValue = getArguments().getDouble("bmi_data");
        String bmiValueString = Double.toString(bmiValue);

        //Set the text in the fragment
        mTvBMIData.setText("" + bmiValueString);

        return fragmentView;
    }
}