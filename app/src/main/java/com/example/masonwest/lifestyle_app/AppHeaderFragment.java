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


/**
 */
public class AppHeaderFragment extends Fragment implements View.OnClickListener{

    // Member Variables
    private String mFirstName, mLastName, mFullName, mCity, mCountry, mSex;
    private int mWeight, mHeight, mAge;
    private TextView mTvFirstName, mTvLastName, mTvAge;
    private ImageView mIvPicture;
    private Bundle pictureBundle;
    private Bitmap thumbNail;
    private ImageButton mButtonSettings;
    OnDataPass mDataPasser;

    public AppHeaderFragment() {

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (OnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }
    public interface OnDataPass {
        void OnDataPass(String firstName, String lastName, String city, String country, String sex, int age, int weight, int height, Bundle pictureBundle);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_header, container, false);

        //Get the text views and image view
        mTvFirstName = (TextView) view.findViewById(R.id.tv_fn_data);
//        mTvLastName = (TextView) view.findViewById(R.id.tv_ln_data);
        mIvPicture = (ImageView) view.findViewById(R.id.iv_pic);
        mButtonSettings = view.findViewById(R.id.settingsButton);
        mButtonSettings.setOnClickListener(this);
        //FOR LIFECYCLE AWARENESS LOOK AT HW2 PART1 DATASUMMARY.JAVA
        //Get the data that was sent in via OnDataPass
        mFirstName = getArguments().getString("userFirstName");
        mLastName = getArguments().getString("userLastName");
        mFullName = getArguments().getString("userFullName");
        mAge = getArguments().getInt("userAge");
        mHeight = getArguments().getInt("userHeight");
        mWeight = getArguments().getInt("userWeight");
        mCity = getArguments().getString("userCity");
        mCountry = getArguments().getString("userCountry");
        mSex = getArguments().getString("userSex");
        pictureBundle = getArguments().getBundle("userPic");
        thumbNail = (Bitmap) pictureBundle.get("data");

        //Set the data
        if(mFirstName != null) {
            mTvFirstName.setText("" + mFullName);
        }
//        if(mLastName != null) {
//            mTvLastName.setText("" + mLastName);
//        }
        //if statement to handle the bitmap image
        mIvPicture.setImageBitmap(thumbNail);

        return view;
    }

    @Override
    public void onClick(View view) {

//        Fragment mUserDetailFragment = new EditUserDetailsFragment();
        //Replace the fragment container
//        FragmentTransaction fTrans = getFragmentManager().beginTransaction();
//        fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "submit_frag"); //.getTag()???
//        fTrans.commit();

//        Fragment mSignUpHeaderFragment = new SignUpHeaderFragment();

        //Replace the fragment container
//        fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment, "header_frag"); //.getTag()???
        mDataPasser.OnDataPass(mFirstName, mLastName, mCity, mCountry, mSex, mAge, mWeight, mHeight, pictureBundle);
//        fTrans.commit();
    }
}
