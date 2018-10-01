package com.example.masonwest.lifestyle_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AppHeaderFragment extends Fragment implements View.OnClickListener{

    // Member Variables
//    private String mFirstName, mLastName, mFullName, mCity, mCountry, mSex;
//    private int mWeight, mHeight, mAge;
    private TextView mTvFullName, mTvLastName, mTvAge;
    private ImageView mIvPicture;
//    private Bundle pictureBundle;
//    private Bitmap thumbNail;
    private ImageButton mButtonSettings;
    private HeaderDataPass mDataPasser;
    private Bundle userData;
    private User currentUser;

    public AppHeaderFragment() {

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (HeaderDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement HeaderDataPass");
        }
    }
    public interface HeaderDataPass {
        void HeaderDataPass(User currentUser);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_header, container, false);

        //Get the text views and image view
        mTvFullName = (TextView) view.findViewById(R.id.tv_fn_data);
//        mTvLastName = (TextView) view.findViewById(R.id.tv_ln_data);
        mIvPicture = (ImageView) view.findViewById(R.id.iv_pic);
        mButtonSettings = view.findViewById(R.id.settingsButton);
        mButtonSettings.setOnClickListener(this);

        //FOR LIFECYCLE AWARENESS LOOK AT HW2 PART1 DATASUMMARY.JAVA
        //Get the data that was sent in via HeaderDataPass
        if(savedInstanceState != null) {
            currentUser = savedInstanceState.getParcelable("user");
        } else {
            currentUser = getArguments().getParcelable("user");
        }

//        mFirstName = getArguments().getString("userFirstName");
//        mLastName = getArguments().getString("userLastName");
//        mFullName = getArguments().getString("userFullName");
//        mAge = getArguments().getInt("userAge");
//        mHeight = getArguments().getInt("userHeight");
//        mWeight = getArguments().getInt("userWeight");
//        mCity = getArguments().getString("userCity");
//        mCountry = getArguments().getString("userCountry");
//        mSex = getArguments().getString("userSex");
//        pictureBundle = getArguments().getBundle("userPic");
//        thumbNail = (Bitmap) pictureBundle.get("data");

        //Set the data
        mTvFullName.setText(currentUser.getFullName());
        mIvPicture.setImageBitmap(currentUser.getProfilePic());

        return view;
    }

    @Override
    public void onClick(View view) {
        mDataPasser.HeaderDataPass(currentUser);
    }
}
