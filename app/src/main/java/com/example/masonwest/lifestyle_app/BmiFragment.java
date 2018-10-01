package com.example.masonwest.lifestyle_app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BmiFragment extends Fragment {

    private TextView mTvBMIData;
    private User currentUser;

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
        mTvBMIData = fragmentView.findViewById(R.id.tv_bmi_data);

        String bmiValueString;
        if (savedInstanceState != null) {
            currentUser = savedInstanceState.getParcelable("user");
        }
        else {
            currentUser = getArguments().getParcelable("user");
        }

        double bmiValue = currentUser.getBMI();
        bmiValueString = Double.toString(bmiValue);

        //Set the text in the fragment
        mTvBMIData.setText(bmiValueString);

        return fragmentView;
    }

    /**
     * Allows the page to be lifecycle aware
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Put them in the outgoing Bundle
        outState.putParcelable("user", currentUser);

        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }
}