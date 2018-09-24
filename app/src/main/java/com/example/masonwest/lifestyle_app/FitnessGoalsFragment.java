package com.example.masonwest.lifestyle_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class FitnessGoalsFragment extends Fragment implements View.OnClickListener {

    OnDataPass mDataPasser;
    String mvUserActivityLevel, mvUserSex, mUserFirstName, mUserLastName;
    Spinner activityLevelDropdown, weightChangeDropdown;
    double mvUserBMR, mvUserEnteredGoal, mvUserDailyRecommendedCalorieIntake;
    int mvUserHeight, mvUserAge, mvUserWeight;
    Bitmap mvUserPic;
    TextView activityLevel, weightGoal, tvActualGoal, tvRecommendedCalories;
    public FitnessGoalsFragment() {

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
        void onDataPass(String activityLevel, double BMR, double dailyCalories, double goal);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_fitness_goals, container, false);

        //we need to check if they've already been here before
        //null checks for activity level, change goal

        if(savedInstanceState != null) {
            mUserFirstName = savedInstanceState.getString("userFirstName");
            mUserLastName = savedInstanceState.getString("userLastName");
            mvUserAge = savedInstanceState.getInt("userAge");
            mvUserHeight = savedInstanceState.getInt("userHeight");
            mvUserWeight = savedInstanceState.getInt("userWeight");
            mvUserSex = savedInstanceState.getString("userSex");
            mvUserBMR = savedInstanceState.getDouble("userBMR");
            mvUserEnteredGoal = savedInstanceState.getDouble("userEnteredGoal");
            mvUserDailyRecommendedCalorieIntake = savedInstanceState.getDouble("userCalories");
            mvUserPic = savedInstanceState.getParcelable("userPic");
        } else {
            mUserFirstName = getArguments().getString("userFirstName");
            mUserLastName = getArguments().getString("userLastName");
            mvUserAge = getArguments().getInt("userAge");
            mvUserHeight = getArguments().getInt("userHeight");
            mvUserWeight = getArguments().getInt("userWeight");
            mvUserSex = getArguments().getString("userSex");
            mvUserBMR = getArguments().getDouble("userBMR");
            mvUserEnteredGoal = getArguments().getDouble("userEnteredGoal");
            mvUserDailyRecommendedCalorieIntake = getArguments().getDouble("userCalories");
            mvUserPic = getArguments().getParcelable("userPic");
        }

//        mvUserEnteredGoal; passed from main
//        mvUserDailyRecommendedCalorieIntake; passed from main
//        mvUserActivityLevel = passed from main(so we can know if they've visited this page before)
//        mvUserPic = whatever;

//        mFullName = getArguments().getString("fullName");
//        mAge = getArguments().getString("age");
//        thumbnailImage = getArguments().getParcelable("profilePic");

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

        final String[] finalOptions = activityLevelOptions;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelDropdown.setAdapter(adapter);
        activityLevelDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mvUserActivityLevel = finalOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if(mvUserActivityLevel.equals("")) {
                    activityLevelDropdown.setSelection(2);
                } else {
                    for(int i = 0; i < finalOptions.length; i++) {
                        if(mvUserActivityLevel.equals(finalOptions[i])) {
                            activityLevelDropdown.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });

        //Weekly Gain/Loss
        String [] weightChange = new String[13];
        double valueTemp = -3.0;
        for(int i = 0; i < 13; i++) {
            weightChange[i] = String.valueOf(valueTemp);
            valueTemp += 0.5;
        }
        final String[] finalWeightChange = weightChange;
        weightGoal = fragmentView.findViewById(R.id.tv_weightGoal);
        weightGoal.setText("Please select your weekly weight change goal:");
        weightChangeDropdown = fragmentView.findViewById(R.id.spin_weightChangeDropdown);
        ArrayAdapter<String> goalAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalWeightChange);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightChangeDropdown.setAdapter(goalAdapter);
        weightChangeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mvUserEnteredGoal = Double.parseDouble(finalWeightChange[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                if(mvUserEnteredGoal == 0) {
                    weightChangeDropdown.setSelection(6);
                }
                for(int i = 0; i < finalWeightChange.length; i++) {
                    if(mvUserEnteredGoal == Double.parseDouble(finalWeightChange[i])) {
                        weightChangeDropdown.setSelection(i);
                        break;
                    }
                }
            }
        });


//        RadioButton lossButton = new RadioButton(getContext());
//        lossButton.setText("Loss");
//        RadioButton gainButton = new RadioButton(getContext());
//        gainButton.setText("Gain");
//        RadioButton maintainButton = new RadioButton(getContext());
//        maintainButton.setText("Maintain");
//        maintainButton.setChecked(true);
//
//        RadioGroup radioButtons = fragmentView.findViewById(R.id.rg_radioButtons);
//        radioButtons.addView(gainButton);
//        radioButtons.addView(maintainButton);
//        radioButtons.addView(lossButton);
//
//        radioButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(checkedId == 0) {
//                    mvUserGainLossGoal = "loss";
//                } else if(checkedId == 1){
//                    mvUserGainLossGoal = "maintain";
//                } else {
//                    mvUserGainLossGoal = "gain";
//                }
//            }
//        });

        //Display
        Button calculateBMR = fragmentView.findViewById(R.id.button_calculateBMR);
        calculateBMR.setOnClickListener(this);
        tvActualGoal = fragmentView.findViewById(R.id.tv_actualGoal);
        tvRecommendedCalories = fragmentView.findViewById(R.id.tv_recommendedCalories);
//
//        if(mvUserGainLossGoal == "loss") {
//            tvGoalDescription.setText("Enter weekly loss goal: ");
//            goalEntry = fragmentView.findViewById(R.id.et_goalEntry);
//            mvUserEnteredGoal = Float.valueOf(goalEntry.getText().toString());
//        } else if(mvUserGainLossGoal == "gain") {
//            tvGoalDescription.setText("Enter weekly gain goal:");
//            goalEntry = fragmentView.findViewById(R.id.et_goalEntry);
//            mvUserEnteredGoal = Float.valueOf(goalEntry.getText().toString());
//        }
        return fragmentView;
    }


    @Override
    public void onClick(View view) {

        if(mvUserEnteredGoal > 2) {
            Toast.makeText(getActivity(), "Warning: Losing/Gaining more than 2 pounds", Toast.LENGTH_SHORT).show();
        }

        mvUserBMR = User.calculateBMR(mvUserWeight, mvUserHeight, mvUserAge, mvUserSex);
        if(mvUserEnteredGoal < 0) {
            tvActualGoal.setText("Your weekly goal: Lose " + -mvUserEnteredGoal + " pound(s)");
        } else if(mvUserEnteredGoal == 0) {
            tvActualGoal.setText("Your weekly goal: Maintain current weight");
        } else {
            tvActualGoal.setText("Your weekly goal: Gain " + mvUserEnteredGoal + " pound(s)");
        }

        mvUserDailyRecommendedCalorieIntake = User.calculateDailyRecommendedCalorieIntake(mvUserBMR, mvUserActivityLevel, mvUserEnteredGoal);

        int calorieLimit = mvUserSex.equals("Male") ? 1200 : 1000;
        if(mvUserDailyRecommendedCalorieIntake < calorieLimit) {
            Toast.makeText(getActivity(), "Warning: Potentially low calorie intake", Toast.LENGTH_SHORT).show();
        }

        tvRecommendedCalories.setText("Recommended daily calorie intake: " + mvUserDailyRecommendedCalorieIntake);
        mDataPasser.onDataPass(mvUserActivityLevel, mvUserBMR, mvUserDailyRecommendedCalorieIntake, mvUserEnteredGoal);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt("userAge", mvUserAge);
        outState.putInt("userHeight", mvUserHeight);
        outState.putDouble("userWeight", mvUserWeight);
        outState.putString("userSex", mvUserSex);
        outState.putDouble("userBMR", mvUserBMR);
        outState.putDouble("userEnteredGoal", mvUserEnteredGoal);
        outState.putDouble("userCalories", mvUserDailyRecommendedCalorieIntake);
        outState.putParcelable("userPic", mvUserPic);

        super.onSaveInstanceState(outState);
    }
}
