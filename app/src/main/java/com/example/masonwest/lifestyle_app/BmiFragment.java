package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
//    private User currentUser;
    private UserViewModel mUserViewModel;

    public BmiFragment() {
        // Required empty public constructor
    }

    //create an observer that watches the MutableLiveData<User> object
    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if(user!=null) {
                //what to do if user changes?
                User temp = user;
                double bmiValue = temp.getBMI();
                String bmiValueString = Double.toString(bmiValue);

                //Set the text in the fragment
                mTvBMIData.setText(bmiValueString);
            }
        }
    };

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

        //get the view model
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mUserViewModel.getUser().observe(this, userObserver);

//        double bmiValue = mUserViewModel.getBMI();
//        double bmiValue = mUserViewModel.getUser().getValue().getBMI();
        double bmiValue = 0.0;
        String bmiValueString = Double.toString(bmiValue);

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
//        outState.putParcelable("user", currentUser);

        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }
}