package com.example.masonwest.lifestyle_app;

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
    private Fragment mMasterListFragment, mSignUpHeaderFragment, mAppHeaderFragment, mUserDetailFragment;

    public AppHeaderFragment() {

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
        mTvFullName.setText(mUserViewModel.getFullName());
        mIvPicture.setImageBitmap(mUserViewModel.getProfilePic());

        return view;
    }

    //create an observer that watches the MutableLiveData<User> object
    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if(user!=null) {
                //what to do if user changes?
            }
        }
    };

    @Override
    public void onClick(View view) {

        //Replace the fragment container
        FragmentTransaction fTrans = getFragmentManager().beginTransaction();

        if (isTablet()) {
            fTrans.replace(R.id.fl_frag_edituser_container_tablet, mUserDetailFragment, "frag_detail");
        }
        else {
            fTrans.replace(R.id.fl_frag_masterlist_container_phone, mUserDetailFragment, "frag_detail");
        }
//        showHideFragment(mUserDetailFragment);
//        showHideFragment(mMasterListFragment);
        mSignUpHeaderFragment = new SignUpHeaderFragment();

        //Replace the fragment container
        if (isTablet()) {
            fTrans.replace(R.id.fl_header_tablet, mSignUpHeaderFragment, "frag_signupheader_tablet"); //.getTag()???
        }
        else {
            fTrans.replace(R.id.fl_header_phone, mSignUpHeaderFragment, "frag_signupheader_phone"); //.getTag()???
        }

        fTrans.commit();
    }
    public boolean isTablet()
    {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
