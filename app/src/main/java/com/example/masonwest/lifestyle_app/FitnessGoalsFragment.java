package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class FitnessGoalsFragment extends Fragment implements View.OnClickListener {

//    OnDataPass mDataPasser;
//    User currentUser;
    private UserViewModel mUserViewModel;
    private User mCurrentUser;
//    String mvUserActivityLevel, mvUserSex, mUserFirstName, mUserLastName;
    Spinner activityLevelDropdown, weightChangeDropdown;
    TextView activityLevel, weightGoal, tvActualGoal, tvRecommendedCalories;
    public FitnessGoalsFragment() {

    }

    //create an observer that watches the MutableLiveData<User> object
    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if(user!=null) {
                mCurrentUser = user;
                tvActualGoal.setText(String.valueOf(mCurrentUser.getWeightChangeGoal()));
//                tvRecommendedCalories.setText(String.valueOf(mUserViewModel.getDailyRecommendedCalorieIntake()));
                tvRecommendedCalories.setText(String.valueOf(mCurrentUser.getRecommendedDailyCalorieIntake()));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_fitness_goals, container, false);

        //we need to check if they've already been here before
        //null checks for activity level, change goal
        //Display

        ImageButton calculateBMR = fragmentView.findViewById(R.id.button_calculateBMR);
        calculateBMR.setOnClickListener(this);
        tvActualGoal = fragmentView.findViewById(R.id.tv_actualGoal);
        tvRecommendedCalories = fragmentView.findViewById(R.id.tv_recommendedCalories);
        String goalText = "";
        String calText = "";

        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mUserViewModel.getUser().observe(this, userObserver);

        if(savedInstanceState != null) {
//            goalText = String.valueOf(mUserViewModel.getWeightChangeGoal());
            goalText = String.valueOf(mUserViewModel.getUser().getValue().getWeightChangeGoal());
//            calText = String.valueOf(mUserViewModel.getDailyRecommendedCalorieIntake());
            calText = String.valueOf(mUserViewModel.getUser().getValue().getRecommendedDailyCalorieIntake());
        }

        if(goalText != "") {
            tvActualGoal.setText(goalText);
        }
        if(calText != "") {
            tvRecommendedCalories.setText(calText);
        }

        //Activity Level
        activityLevel = fragmentView.findViewById(R.id.tv_activityLevel);
        // activityLevel.setText("Select activity level:");
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
        activityLevelDropdown.setSelection(2);
        activityLevelDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                mUserViewModel.setActivityLevel(finalOptions[position]);
                mUserViewModel.getUser().getValue().setActivityLevel(finalOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                if(mUserViewModel.getActivityLevel().equals("")) {
//                    activityLevelDropdown.setSelection(2);
//                } else {
//                    for(int i = 0; i < finalOptions.length; i++) {
//                        if(mUserViewModel.getActivityLevel().equals(finalOptions[i])) {
//                            activityLevelDropdown.setSelection(i);
//                            break;
//                        }
//                    }
//                }
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
        weightChangeDropdown = fragmentView.findViewById(R.id.spin_weightChangeDropdown);
        ArrayAdapter<String> goalAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalWeightChange);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightChangeDropdown.setAdapter(goalAdapter);
        weightChangeDropdown.setSelection(6);
        weightChangeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//                mUserViewModel.setWeightChangeGoal(Double.parseDouble(finalWeightChange[position]));
                mUserViewModel.getUser().getValue().setWeightChangeGoal(Double.parseDouble(finalWeightChange[position]));
                if(mUserViewModel.getUser().getValue().getWeightChangeGoal() > 2 || mUserViewModel.getUser().getValue().getWeightChangeGoal() < -2) {
//                if(mUserViewModel.getWeightChangeGoal() > 2 || mUserViewModel.getWeightChangeGoal() < -2) {
                    Toast.makeText(getActivity(), "Warning: Losing/Gaining more than 2 pounds", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
//                if(mUserViewModel.getWeightChangeGoal() == 0) {
//                    weightChangeDropdown.setSelection(6);
//                }
//                for(int i = 0; i < finalWeightChange.length; i++) {
//                    if(mUserViewModel.getWeightChangeGoal() == Double.parseDouble(finalWeightChange[i])) {
//                        weightChangeDropdown.setSelection(i);
//                        break;
//                    }
//                }
            }
        });

        return fragmentView;
    }

    @Override
    public void onClick(View view) {
//        int weight = mUserViewModel.getWeight();
        int weight = mUserViewModel.getUser().getValue().getWeightLBS();
//        int height = mUserViewModel.getHeight();
        int height = mUserViewModel.getUser().getValue().getHeightInches();
//        int age = mUserViewModel.getAge();
        int age = mUserViewModel.getUser().getValue().getAge();
//        String sex = mUserViewModel.getSex();
        String sex = mUserViewModel.getUser().getValue().getSex();
//        mUserViewModel.setBMR(User.calculateBMR(weight, height, age, sex));
        mUserViewModel.getUser().getValue().setBMR(User.calculateBMR(weight, height, age, sex));
//        tvActualGoal.setText(String.valueOf(mUserViewModel.getWeightChangeGoal()));
        tvActualGoal.setText(String.valueOf(mUserViewModel.getUser().getValue().getWeightChangeGoal()));
//        double bmr = mUserViewModel.getBMR();
        double bmr = mUserViewModel.getUser().getValue().getBMR();
//        String activityLevel = mUserViewModel.getActivityLevel();
        String activityLevel = mUserViewModel.getUser().getValue().getActivityLevel();
//        double goal = mUserViewModel.getWeightChangeGoal();
        double goal = mUserViewModel.getUser().getValue().getWeightChangeGoal();

//        mUserViewModel.setDailyRecommendedCalorieIntake(User.calculateDailyRecommendedCalorieIntake(bmr, activityLevel, goal));
        mUserViewModel.getUser().getValue().setRecommendedDailyCalorieIntake(User.calculateDailyRecommendedCalorieIntake(bmr, activityLevel, goal));
//        int calories = (int)mUserViewModel.getDailyRecommendedCalorieIntake();
        int calories = (int)mUserViewModel.getUser().getValue().getRecommendedDailyCalorieIntake();
//        int calorieLimit = mUserViewModel.getSex().equals("Male") ? 1200 : 1000;
        int calorieLimit = (mUserViewModel.getUser().getValue().getSex()).equals("Male") ? 1200 : 1000;
        if(calories < calorieLimit) {
            Toast.makeText(getActivity(), "Warning: Potentially low calorie intake", Toast.LENGTH_SHORT).show();
        }
        mUserViewModel.dumpInDB(mUserViewModel.getUser().getValue());
        tvRecommendedCalories.setText(String.valueOf(calories));
//        if (!isTablet()) {
//            mDataPasser.onDataPass(currentUser);
//        }
    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//
//        String goalText = tvActualGoal.getText().toString();
//        String calText = tvRecommendedCalories.getText().toString();
//        outState.putString("goalTextView", goalText);
//        outState.putString("caloriesTextView", calText);
//
//        super.onSaveInstanceState(outState);
//    }

//    public boolean isTablet()
//    {
//        return getResources().getBoolean(R.bool.isTablet);
//    }
}
