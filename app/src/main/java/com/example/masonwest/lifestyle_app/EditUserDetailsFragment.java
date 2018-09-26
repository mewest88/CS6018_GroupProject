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
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private Spinner mSpinnerAge, mSpinnerWeight, mSpinnerHeight, mSpinnerCity, mSpinnerCountry, mSpinnerSex;
    private ImageButton mBtSubmit ;
    private Button mBtPicture;
    private String mFirstName, mLastName, mAgeString, mCity, mCountry, mSex;
    private ImageView mIvPic;
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
            throw new ClassCastException(context.toString() + " must implement HeaderDataPass");
        }
    }

    //Callback interface
    // TODO: needs weight, height, sex, location added
    public interface OnDataPass{
        void onDataPass(String firstName, String lastName, int age, int height, int weight, String city, String country, Bundle thumbnailImage, String sex);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_edit_user_details, container, false);


        //Get the views
        mEtFirstName = (EditText) fragmentView.findViewById(R.id.et_firstName);
        mEtLastName = (EditText) fragmentView.findViewById(R.id.et_lastName);
        mSpinnerAge = (Spinner) fragmentView.findViewById(R.id.et_Age);
        mSpinnerCity = (Spinner) fragmentView.findViewById(R.id.et_City);
        mSpinnerCountry = (Spinner) fragmentView.findViewById(R.id.et_Country);
        mSpinnerSex = (Spinner) fragmentView.findViewById(R.id.et_Sex);
        mSpinnerWeight = (Spinner) fragmentView.findViewById(R.id.et_Weight);
        mSpinnerHeight = (Spinner) fragmentView.findViewById(R.id.et_Height);
        mBtPicture = (Button) fragmentView.findViewById(R.id.button_takePicture);
        mBtSubmit = (ImageButton) fragmentView.findViewById(R.id.button_submit);
        mIvPic = (ImageView) fragmentView.findViewById(R.id.iv_pic);
        mBtSubmit.setOnClickListener(this);
        mBtPicture.setOnClickListener(this);

        
        if (savedInstanceState != null) {
            mFirstName = savedInstanceState.getString("userFirstName");
            mEtFirstName.setText(mFirstName);
            mLastName = savedInstanceState.getString("userLastName");
            mEtLastName.setText(mLastName);
            mCity = savedInstanceState.getString("userCity");
            mCountry = savedInstanceState.getString("userCountry");
            mSex = savedInstanceState.getString("userSex");
            mAge = savedInstanceState.getInt("userAge");
            mHeight = savedInstanceState.getInt("userHeight");
            mWeight = savedInstanceState.getInt("userWeight");
            thumbnailImage = savedInstanceState.getBundle("userPic");
        }


        String[] ageOptions = new String[120];
        for(int i = 0; i < 120; i++) {
            ageOptions[i] = String.valueOf(i + 1);
        }
        final String[] finalAgeOptions = ageOptions;
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalAgeOptions);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAge.setAdapter(ageAdapter);

        mSpinnerAge.setSelection(17);
        mSpinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mAge = Integer.parseInt(finalAgeOptions[position]);
//                if(mAge > 2) {
////                    Toast.makeText(getBaseContext(), weightChange[position], Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerAge.setSelection(1);
            }
        });


        String[] weightOptions = new String[400];
        for(int i = 0; i < 400; i++) {
            weightOptions[i] = String.valueOf(i + 1);
        }
        final String[] finalWeightOptions = weightOptions;
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalWeightOptions);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerWeight.setAdapter(weightAdapter);
        if(mWeight > 1) {
            mSpinnerWeight.setSelection(mWeight - 1);
        } else {
            mSpinnerWeight.setSelection(99);
        }
        mSpinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mWeight = Integer.parseInt(finalWeightOptions[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerWeight.setSelection(149);
            }
        });

        String[] heightOptions = new String[96];
        for(int i = 0; i < 96; i++) {
            heightOptions[i] = String.valueOf(i + 1);
        }
        final String[] finalHeightOptions = heightOptions;
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalHeightOptions);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerHeight.setAdapter(heightAdapter);
        if(mHeight > 1) {
            mSpinnerHeight.setSelection(mHeight - 1);
        } else {
            mSpinnerHeight.setSelection(65);
        }
        mSpinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mHeight = Integer.parseInt(finalHeightOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerCity.setSelection(65);
            }
        });

        String[] cityOptions = new String[8];
        cityOptions[0] = "Salt Lake City";
        cityOptions[1] = "New York";
        cityOptions[2] = "San Francisco";
        cityOptions[3] = "Kamas";
        cityOptions[4] = "Iztapalapa";
        cityOptions[5] = "London";
        cityOptions[6] = "San Juan";
        cityOptions[7] = "Vancouver";

        final String[] finalCityOptions = cityOptions;
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalCityOptions);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCity.setAdapter(cityAdapter);
        if(mCity != null) {
            int index = 0;
            for(int i = 0; i < cityOptions.length; i++) {
                if(cityOptions[i].equals(mCity)) {
                    index = i;
                    break;
                }
                mSpinnerCity.setSelection(index);
            }
        } else {
            mSpinnerCity.setSelection(3);
        }

        mSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mCity = finalCityOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerCity.setSelection(0);
            }
        });

        String[] countryOptions = new String[8]; //Esay to get country codes here - https://openweathermap.org/weathermap?basemap=map&cities=true&layer=temperature&lat=40.3633&lon=-73.3447&zoom=7
        countryOptions[0] = "US";
        countryOptions[1] = "CA";
        countryOptions[2] = "PR";
        countryOptions[3] = "MX";
        countryOptions[4] = "GB";
        countryOptions[5] = "ES";
        countryOptions[6] = "FR";
        countryOptions[7] = "JP";

        final String[] finalCountryOptions = countryOptions;
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalCountryOptions);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCountry.setAdapter(countryAdapter);
        if(mCountry != null) {
            int index = 0;
            for(int i = 0; i < countryOptions.length; i++) {
                if(countryOptions[i].equals(mCountry)) {
                    index = i;
                    break;
                }
            }
            mSpinnerCountry.setSelection(index);
        } else {
            mSpinnerCountry.setSelection(0);
        }
        mSpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mCountry = finalCountryOptions[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerCountry.setSelection(0);
            }
        });

        String[] sexOptions = new String[2];
        sexOptions[0] = "Male";
        sexOptions[1] = "Female";
        final String[] finalSexOptions = sexOptions;
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalSexOptions);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSex.setAdapter(sexAdapter);
        if(mSex != null) {
            if (mSex.equals("Female")) {
                mSpinnerSex.setSelection(1);
            }
        } else {
                mSpinnerSex.setSelection(1);
            }
        mSpinnerSex.setSelection(0);

        mSpinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mSex = finalSexOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerSex.setSelection(0);
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
            mBtPicture.setText("") ;
            mBtPicture.setBackgroundResource(R.drawable.ic_check) ;
        }
    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
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

                //Check if the EditText's for first and last name strings are empty
                if (mFirstName.matches("")) {
                    Toast.makeText(getActivity(), "Enter a first name please!", Toast.LENGTH_SHORT).show();
                } else if (mLastName.matches("")) {
                    Toast.makeText(getActivity(), "Enter a last name please!", Toast.LENGTH_SHORT).show();
                } else if (thumbnailImage == null){
                    Toast.makeText(getActivity(), "Please use the button to take a picture!", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getActivity(), "Good job!", Toast.LENGTH_SHORT).show();
                    //Start an activity and pass the EditText string to it.
                    mDataPasser.onDataPass(mFirstName, mLastName, mAge, mHeight, mWeight, mCity, mCountry, thumbnailImage, mSex);
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
        outState.putString("userFirstName", mFirstName);
        outState.putString("userLastName", mLastName);
        outState.putBundle("userPic", thumbnailImage);
        outState.putInt("userAge", mAge);
        outState.putInt("userWeight", mWeight);
        outState.putInt("userHeight", mHeight);
        outState.putString("userCity", mCity);
        outState.putString("userCountry", mCountry);
        outState.putString("userSex", mSex);

        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }
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

            if (savedInstanceState.getString("userFirstName") == null) {
                mEtFirstName.setText("");
            } else {
                mEtFirstName.setText(savedInstanceState.getString("userFirstName"));
            }
            if (savedInstanceState.getString("userLastName") == null) {
                mEtLastName.setText("");
            } else {
                mEtLastName.setText(savedInstanceState.getString("userLastName"));
            }

            mSex = savedInstanceState.getString("userSex");
            mCity = savedInstanceState.getString("userCity");
            mCountry = savedInstanceState.getString("userCountry");
            mAge = savedInstanceState.getInt("userAge");
            if(savedInstanceState.getParcelable("userPic") != null) {
                thumbnailImage = savedInstanceState.getParcelable("userPic");
            }
        }
    }
}

