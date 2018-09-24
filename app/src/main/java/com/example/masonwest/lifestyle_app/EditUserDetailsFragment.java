package com.example.masonwest.lifestyle_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 */
public class EditUserDetailsFragment extends Fragment
                                    implements View.OnClickListener {

    // TODO: needs weight, height, sex, location added to everything!
    //Member variables
    private EditText mEtFirstName, mEtLastName ;
    private Spinner mEtAge;
    private ImageButton mBtSubmit ;
    private Button mBtPicture;
    private String mFirstName, mLastName, mAgeString, mCity, mCountry;
//    private ImageView mIvPic;
    int mAge, mHeight, mWeight;
    Bundle thumbnailImage;
    Bitmap mProfPic, picture;

    OnDataPass mDataPasser;

    //Define a request code for the camera
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public EditUserDetailsFragment() {

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

    //Callback interface
    // TODO: needs weight, height, sex, location added
    public interface OnDataPass{
        void onDataPass(String firstName, String lastName, int age, int height, int weight, String city, String country, Bundle thumbnailImage);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_edit_user_details, container, false);

        //Get the views
        mEtFirstName = (EditText) fragmentView.findViewById(R.id.et_firstName);
        mEtLastName = (EditText) fragmentView.findViewById(R.id.et_lastName);
        mEtAge = (Spinner) fragmentView.findViewById(R.id.et_Age);
        mBtPicture = (Button) fragmentView.findViewById(R.id.button_takePicture);
        mBtSubmit = (ImageButton) fragmentView.findViewById(R.id.button_submit);
//        mIvPic = (ImageView) fragmentView.findViewById(R.id.iv_pic);
        mBtSubmit.setOnClickListener(this);
        mBtPicture.setOnClickListener(this);

        String[] ageOptions = new String[120];
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, ageOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEtAge.setAdapter(adapter);

        for(int i = 0; i < 120; i++) {
            ageOptions[i] = String.valueOf(i);
        }
        final String[] finalOptions = ageOptions;
        mEtAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mAge = Integer.parseInt(finalOptions[position]);
//                if(mAge > 2) {
////                    Toast.makeText(getBaseContext(), weightChange[position], Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mEtAge.setSelection(1);
            }
        });

        return fragmentView;
    }

    /**
     * Collect the image from the camera and save the thumbnail image to be access on the second page
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK){
            thumbnailImage = data.getExtras();
            mProfPic = (Bitmap) thumbnailImage.get("data");
//            mIvPic.setImageBitmap(mProfPic);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_submit: {
                //Collect the first name, last name
                mFirstName = mEtFirstName.getText().toString();
                mLastName = mEtLastName.getText().toString();

                //Remove any leading spaces or tabs on the first and last name
                mFirstName = mFirstName.replaceAll("^\\s+", "");
                mLastName = mLastName.replaceAll("^\\s+", "");

                //Collect the age
//                mAgeString = mEtAge.getText().toString();
//                mAge = Integer.parseInt(mAgeString);

                //Check if the EditText's for first and last name strings are empty
                if (mFirstName.matches("")) {
                    Toast.makeText(getActivity(), "Enter a first name please!", Toast.LENGTH_SHORT).show();
                } else if (mLastName.matches("")) {
                    Toast.makeText(getActivity(), "Enter a last name please!", Toast.LENGTH_SHORT).show();
//                } else if (mAgeString.matches("")) {
//                    Toast.makeText(getActivity(), "Please enter a valid birth date in the form mm/dd/yyyy.", Toast.LENGTH_SHORT).show();
                    // TODO: Add a line to convert the entered age into an int, than I can do this check easily.
//                } else if (mAge < 0 || mAge > 150) {
//                    Toast.makeText(getActivity(), "Please enter a valid birth date. 0 > Age < 150."
//                            , Toast.LENGTH_SHORT).show();
                } else if (thumbnailImage == null){
                    Toast.makeText(getActivity(), "Please use the button to take a picture!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Good job!", Toast.LENGTH_SHORT).show();
                    //Start an activity and pass the EditText string to it.
                    mDataPasser.onDataPass(mFirstName, mLastName, mAge, mHeight, mWeight, mCity, mCountry, thumbnailImage);
                }
                break;
            }
            case R.id.button_takePicture: {
                //The button press should open a camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    //TODO: Lifecycle awareness is not working properly
    /**
     * Saves the state in case the app is closed or turned sideways
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Collect input data
        mFirstName = mEtFirstName.getText().toString();
        mLastName = mEtLastName.getText().toString();
//        picture = (Bitmap) thumbnailImage.get("data"); //TODO: this is not working right

        //Put them in the outgoing Bundle
        outState.putString("FN_TEXT", mFirstName);
        outState.putString("LN_TEXT", mLastName);
        outState.putParcelable("PIC_DATA", picture);
        outState.putInt("AGE_TEXT", mAge);
        outState.putInt("WEIGHT_TEXT", mWeight);
        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }

    /**
     * Used to restore the app in the case that the state needs to be saved
     * @param savedInstanceState
     */
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        //Restore the view hierarchy automatically
        super.onViewStateRestored(savedInstanceState);

        //check and make sure bundle is not null first
        if(savedInstanceState != null) {
            //Restore stuff
            if ("FN_TEXT" == null) {
                mEtFirstName.setText("");
            } else {
                mEtFirstName.setText(savedInstanceState.getString("FN_TEXT"));
            }
            if ("LN_TEXT" == null) {
                mEtLastName.setText("");
            } else {
                mEtLastName.setText(savedInstanceState.getString("LN_TEXT"));
            }
            mProfPic = savedInstanceState.getParcelable("PIC_DATA");
        }
    }
}

