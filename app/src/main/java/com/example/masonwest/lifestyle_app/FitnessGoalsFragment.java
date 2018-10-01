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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class FitnessGoalsFragment extends Fragment implements View.OnClickListener {

    OnDataPass mDataPasser;
    User currentUser;
//    String mvUserActivityLevel, mvUserSex, mUserFirstName, mUserLastName;
    Spinner activityLevelDropdown, weightChangeDropdown;
//    double mvUserBMR, mvUserEnteredGoal, mvUserDailyRecommendedCalorieIntake;
//    int mvUserHeight, mvUserAge, mvUserWeight;
//    Bitmap mvUserPic;
    TextView activityLevel, weightGoal, tvActualGoal, tvRecommendedCalories;
    public FitnessGoalsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //TODO: commented this out to make this fragment work on the tablet
        if(!isTablet()) {
            try {
                mDataPasser = (OnDataPass) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement HeaderDataPass");
            }
        }
    }

    //Callback interface
    // TODO: needs weight, height, sex, location added
    public interface OnDataPass{
        void onDataPass(User currentUser);
    }

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
        if(savedInstanceState != null) {
            currentUser = savedInstanceState.getParcelable("user");
            goalText = savedInstanceState.getString("goalTextView");
            calText = savedInstanceState.getString("caloriesTextView");
//            mUserFirstName = savedInstanceState.getString("userFirstName");
//            mUserLastName = savedInstanceState.getString("userLastName");
//            mvUserAge = savedInstanceState.getInt("userAge");
//            mvUserHeight = savedInstanceState.getInt("userHeight");
//            mvUserWeight = savedInstanceState.getInt("userWeight");
//            mvUserSex = savedInstanceState.getString("userSex");
//            mvUserBMR = savedInstanceState.getDouble("userBMR");
//            mvUserEnteredGoal = savedInstanceState.getDouble("userEnteredGoal");
//            mvUserDailyRecommendedCalorieIntake = savedInstanceState.getDouble("userCalories");
//            mvUserPic = savedInstanceState.getParcelable("userPic");
        } else {
            currentUser = getArguments().getParcelable("user");
//            mUserFirstName = getArguments().getString("userFirstName");
//            mUserLastName = getArguments().getString("userLastName");
//            mvUserAge = getArguments().getInt("userAge");
//            mvUserHeight = getArguments().getInt("userHeight");
//            mvUserWeight = getArguments().getInt("userWeight");
//            mvUserSex = getArguments().getString("userSex");
//            mvUserPic = getArguments().getParcelable("userPic");
        }

        if(goalText != "") {
            tvActualGoal.setText(goalText);
        }
        if(calText != "") {
            tvRecommendedCalories.setText(calText);
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
        activityLevelDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                currentUser.setActivityLevel(finalOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if(currentUser.getActivityLevel().equals("")) {
                    activityLevelDropdown.setSelection(2);
                } else {
                    for(int i = 0; i < finalOptions.length; i++) {
                        if(currentUser.getActivityLevel().equals(finalOptions[i])) {
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
        weightChangeDropdown = fragmentView.findViewById(R.id.spin_weightChangeDropdown);
        ArrayAdapter<String> goalAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalWeightChange);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightChangeDropdown.setAdapter(goalAdapter);
        weightChangeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                currentUser.setWeightChangeGoal(Double.parseDouble(finalWeightChange[position]));
                if(currentUser.getWeightChangeGoal() > 2 || currentUser.getWeightChangeGoal() < -2) {
                    Toast.makeText(getActivity(), "Warning: Losing/Gaining more than 2 pounds", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                if(currentUser.getWeightChangeGoal() == 0) {
                    weightChangeDropdown.setSelection(6);
                }
                for(int i = 0; i < finalWeightChange.length; i++) {
                    if(currentUser.getWeightChangeGoal() == Double.parseDouble(finalWeightChange[i])) {
                        weightChangeDropdown.setSelection(i);
                        break;
                    }
                }
            }
        });

        return fragmentView;
    }


    @Override
    public void onClick(View view) {

        int weight = currentUser.getWeight();
        int height = currentUser.getHeight();
        int age = currentUser.getAge();
        String sex = currentUser.getSex();
        currentUser.setBMR(User.calculateBMR(weight, height, age, sex));
        tvActualGoal.setText(String.valueOf(currentUser.getWeightChangeGoal()));

        double bmr = currentUser.getBMR();
        String activityLevel = currentUser.getActivityLevel();
        double goal = currentUser.getWeightChangeGoal();
        currentUser.setDailyRecommendedCalorieIntake(User.calculateDailyRecommendedCalorieIntake(bmr, activityLevel, goal));
        int calories = (int) currentUser.getDailyRecommendedCalorieIntake();
        int calorieLimit = currentUser.getSex().equals("Male") ? 1200 : 1000;
        if(calories < calorieLimit) {
            Toast.makeText(getActivity(), "Warning: Potentially low calorie intake", Toast.LENGTH_SHORT).show();
        }

        tvRecommendedCalories.setText(calories + " cal");
        if (!isTablet()) {
            mDataPasser.onDataPass(currentUser);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        String goalText = tvActualGoal.getText().toString();
        String calText = tvRecommendedCalories.getText().toString();
//        outState.putInt("userAge", mvUserAge);
//        outState.putInt("userHeight", mvUserHeight);
//        outState.putDouble("userWeight", mvUserWeight);
//        outState.putString("userSex", mvUserSex);
//        outState.putDouble("userBMR", mvUserBMR);
//        outState.putDouble("userEnteredGoal", mvUserEnteredGoal);
        outState.putString("goalTextView", goalText);
        outState.putString("caloriesTextView", calText);
//        outState.putDouble("userCalories", mvUserDailyRecommendedCalorieIntake);
        outState.putParcelable("user", currentUser);

        super.onSaveInstanceState(outState);
    }

    public boolean isTablet()
    {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
