package com.example.masonwest.lifestyle_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 */
public class AppHeaderFragment extends Fragment {

    // Member Variables
    private String mFirstName, mLastName;
    private TextView mTvFirstName, mTvLastName, mTvAge;
    private ImageView mIvPicture;
    private Bundle pictureBundle;
    private Bitmap thumbNail;

    public AppHeaderFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_header, container, false);

        //Get the text views and image view
        mTvFirstName = (TextView) view.findViewById(R.id.tv_fn_data);
        mTvLastName = (TextView) view.findViewById(R.id.tv_ln_data);
        mIvPicture = (ImageView) view.findViewById(R.id.iv_pic);

        //FOR LIFECYCLE AWARENESS LOOK AT HW2 PART1 DATASUMMARY.JAVA
        //Get the data that was sent in via onDataPass
        mFirstName = getArguments().getString("FN_DATA");
        mLastName = getArguments().getString("LN_DATA");
        pictureBundle = getArguments().getBundle("PIC_DATA");
        thumbNail = (Bitmap) pictureBundle.get("data");

        //Set the data
        if(mFirstName != null) {
            mTvFirstName.setText("" + mFirstName);
        }
        if(mLastName != null) {
            mTvLastName.setText("" + mLastName);
        }
        //if statement to handle the bitmap image
        mIvPicture.setImageBitmap(thumbNail);

        return view;
    }

}
