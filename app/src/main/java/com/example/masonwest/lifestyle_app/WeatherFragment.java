package com.example.masonwest.lifestyle_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeatherFragment extends Fragment {
//    private TextView mTvBMIData;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_weather, container, false);

        //Get the views
//        mTvBMIData = (TextView) fragmentView.findViewById(R.id.tv_bmi_data);

        //Get the strings to display
        //get the users BMI info from the user class

        return fragmentView;
    }
}
