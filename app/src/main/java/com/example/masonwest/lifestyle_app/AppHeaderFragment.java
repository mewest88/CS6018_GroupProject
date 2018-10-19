package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AppHeaderFragment extends Fragment implements View.OnClickListener{

    // Member Variables
    private TextView mTvFullName;
    private ImageView mIvPicture;
    private ImageButton mButtonSettings;
    private UserViewModel mUserViewModel;
    private OnDataPass dataPasser;
    private LiveData<User> mUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }
    public void passData() {
        dataPasser.onSettingsButtonClick();
    }

    public AppHeaderFragment() {

    }

    public interface OnDataPass {
        void onSettingsButtonClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_header, container, false);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mUserViewModel.getUser().observe(this, userObserver);

        //Get the text views and image view
        mTvFullName = (TextView) view.findViewById(R.id.tv_fn_data);
        mIvPicture = (ImageView) view.findViewById(R.id.iv_pic);
        mButtonSettings = view.findViewById(R.id.settingsButton);
        mButtonSettings.setOnClickListener(this);

        //Set the data
//        mTvFullName.setText(mUserViewModel.getFullName());
//        mTvFullName.setText(mUserViewModel.getUser().getValue().getFullName());
//        mIvPicture.setImageBitmap(mUserViewModel.getProfilePic());
//        mIvPicture.setImageBitmap(mUserViewModel.getUser().getValue().getProfileImageDataInBitmap());

        return view;
    }

    //create an observer that watches the MutableLiveData<User> object
    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if(user!=null) {
//                mTvFullName.setText(mUserViewModel.getFullName());
//                mIvPicture.setImageBitmap(mUserViewModel.getProfilePic());
                mTvFullName.setText(mUserViewModel.getUser().getValue().getFullName());
                mIvPicture.setImageBitmap(mUserViewModel.getUser().getValue().getProfileImageDataInBitmap());
            }
        }
    };

    @Override
    public void onClick(View view) {
        passData();
    }
}
