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

import java.util.concurrent.ExecutionException;

public class EditUserDetailsFragment extends Fragment
                                    implements View.OnClickListener {

    //Member variables
    private EditText mEtFirstName, mEtLastName ;
    private Spinner mSpinnerAge, mSpinnerWeight, mSpinnerHeight, mSpinnerCity, mSpinnerCountry, mSpinnerSex;
    private ImageButton mBtSubmit ;
    private Button mBtPicture;
    private String mFirstName, mLastName;
    private UserViewModel mUserViewModel;
    private OnDataPass dataPasser;
    private String[] countryOptions = new String[8];
    private String[] cityOptions = new String[8];
    private String[] sexOptions = new String[2];
    private String[] ageOptions = new String[120];
    private String[] heightOptions = new String[96];
    private String[] weightOptions = new String[400];

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

    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if (user != null) {
                observeUserAge();
                observeUserWeight();
                observeUserHeight();
                observeUserCity();
                observeUserCountry();
                observeUserSex();
                observeUserProfilePic();
                observeUserName();
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

        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        mUserViewModel.getUser().observe(this, userObserver);

        VoidAsyncTask task = mUserViewModel.getNumberOfUserInDatabase();
        task.execute();

        int numUsersInDB = 0;

        try {
            numUsersInDB = (int) task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        mBtSubmit.setOnClickListener(this);
        mBtPicture.setOnClickListener(this);

        if(mUserViewModel.getUser().getValue() != null) {
            observeUserName();
            observeUserProfilePic();
        }
        buildAgeSpinner();
        buildWeightSpinner();
        buildHeightSpinner();
        buildCitySpinner();
        buildCountrySpinner();
        buildSexSpinner();

        return fragmentView;
    }
    /**
     * Collect the image from the camera and save the thumbnail image to be access on the second page
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK){
            Bundle thumbnailImage = data.getExtras();
            mUserViewModel.getUser().getValue().setProfileImageData((Bitmap)thumbnailImage.get("data"));
            mBtPicture.setBackgroundResource(R.drawable.ic_check);
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
                } else if (mUserViewModel.getUser().getValue().getProfileImageData() == null){
                    Toast.makeText(getActivity(), "Please use the button to take a picture!", Toast.LENGTH_SHORT).show();
                } else {
                    //Start an activity and pass the EditText string to it.
                    double bmi = User.calculateBMI(mUserViewModel.getUser().getValue().getWeightLBS(), mUserViewModel.getUser().getValue().getHeightInches());
                    mUserViewModel.getUser().getValue().setBMI(bmi);
                    mUserViewModel.getUser().getValue().setFirstName(mFirstName);
                    mUserViewModel.getUser().getValue().setLastName(mLastName);
                    mUserViewModel.getUser().getValue().setFullName(mFirstName, mLastName);
                    mUserViewModel.dumpInDB(mUserViewModel.getUser().getValue());
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

        //add a null user check in case rotated during load?
//        if(mUserViewModel.getUser().getValue() != null) {
//            mUserViewModel.getUser().getValue().setFirstName(mFirstName);
//            mUserViewModel.getUser().getValue().setLastName(mLastName);
//        }
        
        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }
    public boolean isTablet()
    {
        return getResources().getBoolean(R.bool.isTablet);
    }
    public void observeUserAge() {
        if (mUserViewModel.getUser().getValue().getAge() > 1) {
            mSpinnerAge.setSelection(mUserViewModel.getUser().getValue().getAge() - 1);
        } else {
            mSpinnerAge.setSelection(17);
            mUserViewModel.getUser().getValue().setAge(18);
        }
    }
    public void observeUserWeight() {
        if (mUserViewModel.getUser().getValue().getWeightLBS() > 1) {
            mSpinnerWeight.setSelection(mUserViewModel.getUser().getValue().getWeightLBS() - 1);
        } else {
            mSpinnerWeight.setSelection(99);
            mUserViewModel.getUser().getValue().setWeightLBS(Integer.parseInt(weightOptions[99]));
        }
    }
    public void observeUserCity() {
        if (mUserViewModel.getUser().getValue().getCity() != null) {
            int index = 0;
            for (int i = 0; i < cityOptions.length; i++) {
                if (cityOptions[i].equa
                ls(mUserViewModel.getUser().getValue().getCity())) {
                    index = i;
                    continue;
                }
                mSpinnerCity.setSelection(index);
            }
        } else {
            mSpinnerCity.setSelection(3);
            mUserViewModel.getUser().getValue().setCity(cityOptions[3]);
        }
    }
    public void observeUserCountry() {
        if (mUserViewModel.getUser().getValue().getCountry() != null) {
            int index = 0;
            for (int i = 0; i < countryOptions.length; i++) {
                if (countryOptions[i].equals(mUserViewModel.getUser().getValue().getCountry())) {
                    index = i;
                    break;
                }
            }
            mSpinnerCountry.setSelection(index);
        } else {
            mSpinnerCountry.setSelection(0);
            mUserViewModel.getUser().getValue().setCountry(countryOptions[0]);
        }
    }
    public void observeUserSex() {
        if (mUserViewModel.getUser().getValue().getSex() != null) {
            if (mUserViewModel.getUser().getValue().getSex().equals(sexOptions[1])) {
                mSpinnerSex.setSelection(1);
            }
        } else {
            mSpinnerSex.setSelection(0);
            mUserViewModel.getUser().getValue().setSex(sexOptions[0]);
        }
    }
    public void observeUserHeight() {
        if (mUserViewModel.getUser().getValue().getHeightInches() > 1) {
            mSpinnerHeight.setSelection(mUserViewModel.getUser().getValue().getHeightInches() - 1);
        } else {
            mSpinnerHeight.setSelection(65);
            mUserViewModel.getUser().getValue().setHeightInches(Integer.parseInt(heightOptions[65]));
        }
    }
    public void observeUserProfilePic() {
        if(mUserViewModel.getUser().getValue().getProfileImageData() != null) {
            mBtPicture.setBackgroundResource(R.drawable.ic_check);
        }
    }
    public void observeUserName() {
        mEtFirstName.setText(mUserViewModel.getUser().getValue().getFirstName());
        mEtLastName.setText(mUserViewModel.getUser().getValue().getLastName());
    }
    public void buildAgeSpinner() {
        for (int i = 0; i < 120; i++) {
            ageOptions[i] = String.valueOf(i + 1);
        }
        final String[] finalAgeOptions = ageOptions;
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalAgeOptions);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAge.setAdapter(ageAdapter);

        mSpinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setAge(Integer.parseInt(finalAgeOptions[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerAge.setSelection(1);
            }
        });
    }
    public void buildWeightSpinner() {
        for (int i = 0; i < 400; i++) {
            weightOptions[i] = String.valueOf(i + 1);
        }
        final String[] finalWeightOptions = weightOptions;
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalWeightOptions);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerWeight.setAdapter(weightAdapter);

        mSpinnerWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//                mUserViewModel.getUser().getValue().setWeightLBS(Integer.parseInt(finalWeightOptions[position]));
                mUserViewModel.getUser().getValue().setWeightLBS(Integer.parseInt(finalWeightOptions[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerWeight.setSelection(149);
            }
        });
    }
    public void buildHeightSpinner() {
        for (int i = 0; i < 96; i++) {
            heightOptions[i] = String.valueOf(i + 1);
        }
        final String[] finalHeightOptions = heightOptions;
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalHeightOptions);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerHeight.setAdapter(heightAdapter);

        mSpinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setHeightInches(Integer.parseInt(finalHeightOptions[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerCity.setSelection(65);
            }
        });
    }
    public void buildCitySpinner() {
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

        mSpinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setCity(finalCityOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerCity.setSelection(0);
            }
        });
    }
    public void buildCountrySpinner() {
        //Easy to get country codes here - https://openweathermap.org/weathermap?basemap=map&cities=true&layer=temperature&lat=40.3633&lon=-73.3447&zoom=7
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

        mSpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setCountry(finalCountryOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerCountry.setSelection(0);
            }
        });
    }
    public void buildSexSpinner() {
        final String[] finalSexOptions = sexOptions;
        sexOptions[0] = "Male";
        sexOptions[1] = "Female";
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalSexOptions);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSex.setAdapter(sexAdapter);

        mSpinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setSex(finalSexOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSpinnerSex.setSelection(0);
            }
        });
    }
}



