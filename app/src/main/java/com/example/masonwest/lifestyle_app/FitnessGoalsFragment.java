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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class FitnessGoalsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditUserDetailsFragment.OnDataPass mDataPasser;
    String setActivityLevel = "";
    Spinner activityLevelDropdown;
    User tempUser;
    String setGoal = "";
    EditText goalEntry;
    float userWeight = 0;
    TextView activityLevel, weightGoal, goalDisplay, tvActualGoal, currentWeight, tvRecommendedCalories;
    public FitnessGoalsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mDataPasser = (EditUserDetailsFragment.OnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    //Callback interface
    // TODO: needs weight, height, sex, location added
    public interface OnDataPass{
        public void onDataPass(String firstName, String lastName, int age, Bundle thumbnailImage);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_edit_user_details, container, false);

        //Get the views
//        mEtFirstName = (EditText) fragmentView.findViewById(R.id.et_firstName);
//        mEtLastName = (EditText) fragmentView.findViewById(R.id.et_lastName);
//        mEtAge = (EditText) fragmentView.findViewById(R.id.et_Age);
//        mBtPicture = (Button) fragmentView.findViewById(R.id.button_takePicture);
//        mBtSubmit = (Button) fragmentView.findViewById(R.id.button_submit);
//        mIvPic = (ImageView) fragmentView.findViewById(R.id.iv_pic);
//        mBtSubmit.setOnClickListener(this);
//        mBtPicture.setOnClickListener(this);

        //Activity Level
        activityLevel = fragmentView.findViewById(R.id.tv_activityLevel);
        activityLevel.setText("Select activity level:");
        activityLevelDropdown = fragmentView.findViewById(R.id.spin_activityLevelDropdown);
        String [] activityLevelOptions = new String[5];
        activityLevelOptions[0] = "Sedentary";
        activityLevelOptions[1] = "Lightly Active";
        activityLevelOptions[2] = "Moderately Active";
        activityLevelOptions[3] = "Very Active";
        activityLevelOptions[4] = "Extra Active";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, activityLevelOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelDropdown.setAdapter(adapter);
        activityLevelDropdown.setOnItemSelectedListener(this);

        //Target Weight, Weekly Gain/Loss
        weightGoal = fragmentView.findViewById(R.id.tv_weightGoal);
        weightGoal.setText("Please select your goal type:");
        RadioButton targetButton = new RadioButton(getContext());
        targetButton.setText("Target Weight");
        RadioButton weeklyButton = new RadioButton(getContext());
        weeklyButton.setText("Weekly Goal");
        RadioGroup radioButtons = fragmentView.findViewById(R.id.rg_radioButtons);
        radioButtons.addView(targetButton);
        radioButtons.addView(weeklyButton);

        radioButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == 0) {
                    setGoal = "targetWeight";
                } else {
                    setGoal = "weeklyGoal";
                }
            }
        });
        tempUser.updateFitnessGoal(setGoal);

        //Display
        goalDisplay = fragmentView.findViewById(R.id.tv_targetWeight);
        goalEntry = fragmentView.findViewById(R.id.et_goalEntry);
        Button calculateBMR = fragmentView.findViewById(R.id.button_calculateBMR);
        tvActualGoal = fragmentView.findViewById(R.id.tv_actualGoal);

        currentWeight = fragmentView.findViewById(R.id.tv_currentWeight);
        tvRecommendedCalories = fragmentView.findViewById(R.id.tv_recommendedCalories);

        if(setGoal == "targetWeight") {
            goalDisplay.setText("Enter target weight in lbs:");
        }
        else {
            goalDisplay.setText("Enter weekly gain/loss goal in lbs");
        }

        return fragmentView;
    }


    @Override
    public void onClick(View view) {
        float enteredWeight = Float.valueOf(goalEntry.getText().toString());
        switch (view.getId()) {
            case R.id.button_calculateBMR: {
                if(setGoal == "targetWeight") {
                    tempUser.updateTargetWeight(enteredWeight);
                    tempUser.calculateBMR(enteredWeight);
                    tempUser.calculateRecommendedCalorieIntake();
                    tempUser.adjustRecommendedCalorieIntakeByTotalGoal();
                }
                else {
                    tempUser.calculateBMR(userWeight);
                    tempUser.updateWeeklyGainLoss(enteredWeight);
                    tempUser.calculateRecommendedCalorieIntake();
                    tempUser.adjustRecommendedCalorieIntakeByWeeklyGoal();
                }
            }
        }

        if(tempUser.getWeightChangeGoal() > 2) {
            //toast warning
        }
        int calorieLimit = tempUser.getSex() == "Male" ? 1200 : 1000;
        if(tempUser.getRecommendedCalorieIntake() < calorieLimit) {
            //toast warning
        }
        currentWeight.setText("Current weight: " + currentWeight);
        tvActualGoal.setText("Target weekly weight change: " + tempUser.getWeightChangeGoal());
        tvRecommendedCalories.setText("Recommended daily calorie intake: " + tempUser.getRecommendedCalorieIntake());
    }
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                setActivityLevel = "Sedentary";
                break;
            case 1:
                setActivityLevel = "Lightly Active";
                break;
            case 2:
                setActivityLevel = "Moderately Active";
                break;
            case 3:
                setActivityLevel = "Very Active";
                break;
            case 4:
                setActivityLevel = "Extra Active";
                break;
        }
        tempUser.updateActivityLevel(setActivityLevel);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        activityLevelDropdown.setSelection(2);
    }
}
