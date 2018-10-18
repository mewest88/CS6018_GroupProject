package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

public class EditUserDetailsFragment extends Fragment
                                    implements View.OnClickListener {

    //Member variables
    private EditText mEtFirstName, mEtLastName ;
    private Spinner mSpinnerAge, mSpinnerWeight, mSpinnerHeight, mSpinnerCity, mSpinnerCountry, mSpinnerSex;
    private ImageButton mBtSubmit ;
    private Button mBtPicture;
    private String mFirstName, mLastName;
    private ImageView mIvPic;
    private UserViewModel mUserViewModel;
    private OnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }
    public void passData() {
        dataPasser.onEditUserSubmit();
    }

    //Define a request code for the camera
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //create an observer that watches the MutableLiveData<User> object
    //possibly not necessary here?
    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if(user!=null) {
                //what to do if user changes?
            }
        }
    };

    public interface OnDataPass {
        void onEditUserSubmit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_edit_user_details, container, false);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mUserViewModel.getUser().observe(this, userObserver);

        if(mUserViewModel.getUser() == null) {
            User newUser = new User(13);
            mUserViewModel.setUser(newUser);
        }

        //Get the views
        mEtFirstName = (EditText) fragmentView.findViewById(R.id.et_firstName);
        mEtFirstName.setText(mUserViewModel.getFirstName());
        mEtLastName = (EditText) fragmentView.findViewById(R.id.et_lastName);
        mEtLastName.setText(mUserViewModel.getLastName());
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

        String[] ageOptions = new String[120];
        for(int i = 0; i < 120; i++) {
            ageOptions[i] = String.valueOf(i + 1);
        }

        String[] heightOptions = new String[96];
        for(int i = 0; i < 96; i++) {
            heightOptions[i] = String.valueOf(i + 1);
        }

        String[] weightOptions = new String[400];
        for(int i = 0; i < 400; i++) {
            weightOptions[i] = String.valueOf(i + 1);
        }

        String[] cityOptions = new String[8];
        cityOptions[0] = "Salt Lake City";
        cityOptions[1] = "New York";
        cityOptions[2] = "San Francisco";
        cityOptions[3] = "Kamas";
        cityOptions[4] = "Iztapalapa";
        cityOptions[5] = "London";
        cityOptions[6] = "San Juan";
        cityOptions[7] = "Vancouver";

        String[] countryOptions = new String[8]; //Easy to get country codes here - https://openweathermap.org/weathermap?basemap=map&cities=true&layer=temperature&lat=40.3633&lon=-73.3447&zoom=7
        countryOptions[0] = "US";
        countryOptions[1] = "CA";
        countryOptions[2] = "PR";
        countryOptions[3] = "MX";
        countryOptions[4] = "GB";
        countryOptions[5] = "ES";
        countryOptions[6] = "FR";
        countryOptions[7] = "JP";

        String[] sexOptions = new String[2];
        sexOptions[0] = "Male";
        sexOptions[1] = "Female";

        final String[] finalAgeOptions = ageOptions;
        final String[] finalHeightOptions = heightOptions;
        final String[] finalWeightOptions = weightOptions;
        final String[] finalCityOptions = cityOptions;
        final String[] finalSexOptions = sexOptions;
        final String[] finalCountryOptions = countryOptions;

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalAgeOptions);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalCityOptions);
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalWeightOptions);
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalHeightOptions);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalCountryOptions);
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalSexOptions);

        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerAge.setAdapter(ageAdapter);
        mSpinnerCity.setAdapter(cityAdapter);
        mSpinnerWeight.setAdapter(weightAdapter);
        mSpinnerHeight.setAdapter(heightAdapter);
        mSpinnerCountry.setAdapter(countryAdapter);
        mSpinnerSex.setAdapter(sexAdapter);

        if(mUserViewModel != null && mUserViewModel.getUser() != null) {

//            User temp = mUserViewModel.getUser().getValue();
            if(mUserViewModel.getAge() > 1) {
                mSpinnerAge.setSelection(mUserViewModel.getAge() - 1);
            } else {
                mSpinnerAge.setSelection(17);
            }

            if(mUserViewModel.getWeight() > 1) {
                mSpinnerWeight.setSelection(mUserViewModel.getWeight() - 1);
            } else {
                mSpinnerWeight.setSelection(99);
            }

            if(mUserViewModel.getHeight() > 1) {
                mSpinnerHeight.setSelection(mUserViewModel.getHeight() - 1);
            } else {
                mSpinnerHeight.setSelection(65);
            }

            if(mUserViewModel.getSex() != null) {
                if (mUserViewModel.getSex().equals("Female")) {
                    mSpinnerSex.setSelection(1);
                }
            } else {
                mSpinnerSex.setSelection(0);
            }

            if(mUserViewModel.getCountry() != null) {
                int index = 0;
                for(int i = 0; i < countryOptions.length; i++) {
                    if(countryOptions[i].equals(mUserViewModel.getCountry())) {
                        index = i;
                        break;
                    }
                }
                mSpinnerCountry.setSelection(index);
            } else {
                mSpinnerCountry.setSelection(0);
            }

            if(mUserViewModel.getCity() != null) {
                int index = 0;
                for(int i = 0; i < cityOptions.length; i++) {
                    if(cityOptions[i].equals(mUserViewModel.getCity())) {
                        index = i;
                        break;
                    }
                    mSpinnerCity.setSelection(index);
                }
            } else {
                mSpinnerCity.setSelection(3);
            }
        }

        mSpinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.setAge(Integer.parseInt(finalAgeOptions[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerAge.setSelection(1);
            }
        });

        mSpinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.setWeight(Integer.parseInt(finalWeightOptions[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerWeight.setSelection(149);
            }
        });

        mSpinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.setHeight(Integer.parseInt(finalHeightOptions[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerCity.setSelection(65);
            }
        });

        mSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.setCity(finalCityOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerCity.setSelection(0);
            }
        });

        mSpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.setCountry(finalCountryOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerCountry.setSelection(0);
            }
        });

        mSpinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//                mSex = finalSexOptions[position];
                mUserViewModel.setSex(finalSexOptions[position]);
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
            Bundle thumbnailImage = data.getExtras();
            mUserViewModel.setProfilePic((Bitmap)thumbnailImage.get("data"));
            mBtPicture.setBackgroundResource(R.drawable.ic_check) ;
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

                //Check if the EditText's for first and last name strings are empty
                if (mFirstName.matches("")) {
                    Toast.makeText(getActivity(), "Enter a first name please!", Toast.LENGTH_SHORT).show();
                } else if (mLastName.matches("")) {
                    Toast.makeText(getActivity(), "Enter a last name please!", Toast.LENGTH_SHORT).show();
                } else if (mUserViewModel.getProfilePic() == null){
                    Toast.makeText(getActivity(), "Please use the button to take a picture!", Toast.LENGTH_SHORT).show();
                } else {
                    //Start an activity and pass the EditText string to it.
                    double bmi = User.calculateBMI(mUserViewModel.getWeight(), mUserViewModel.getHeight());
                    mUserViewModel.setBMI(bmi);
                    mUserViewModel.setFirstName(mFirstName);
                    mUserViewModel.setLastName(mLastName);
                    passData();
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

    /**
     * Saves the state in case the app is closed or turned sideways
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Collect input data (other data was input to the bundle through spinners)
        mFirstName = mEtFirstName.getText().toString();
        mLastName = mEtLastName.getText().toString();
        mUserViewModel.setFirstName(mFirstName);
        mUserViewModel.setLastName(mLastName);

        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }
    public boolean isTablet()
    {
        return getResources().getBoolean(R.bool.isTablet);
    }
}

