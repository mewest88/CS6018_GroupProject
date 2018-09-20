package com.example.masonwest.lifestyle_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SignUpHeaderFragment extends Fragment {

    private TextView mTvHeaderText;

    public SignUpHeaderFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_header, container, false);

        //Get the TextView
        mTvHeaderText = (TextView) view.findViewById(R.id.tv_fn_data);

        return view;
    }
}
