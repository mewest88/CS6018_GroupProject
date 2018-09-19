package com.example.masonwest.lifestyle_app;

import android.graphics.Bitmap;

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private int age;
    private int heightInches;
    private float weightLBS;
    private String city;
    private String country;
    private Enum sex; // male, female
    private Bitmap profilePic;
    private float BMR;
    private float BMI;
    private Enum activityLevel; //sedentary, active
    private Enum fitnessGoal; //lose, gain, maintain
    private float weightChangeGoal; //positive or negative based on fitness goal?
    private float desiredWeight; //potentially not used?
    private int recommendedCalorieIntake;

    User(int userIDPassed, String firstNamePassed, String lastNamePassed, int agePassed, int heightPassed, float weightPassed, String cityPassed, String countryPassed, Enum sexPassed, Bitmap profilePicPassed) {
        userID = userIDPassed;
        firstName = firstNamePassed;
        lastName = lastNamePassed;
        age = agePassed;
        heightInches = heightPassed;
        weightLBS = weightPassed;
        city = cityPassed;
        country = countryPassed;
        sex = sexPassed;
        profilePic = profilePicPassed;
        BMI = calculateBMI();
    }
    public float calculateBMI() {
        if(weightLBS != 0 && heightInches != 0) {
            return weightLBS/heightInches;
        }
        return 0; //should be error?
    }
    public void calculateBMR() {
        BMR = weightLBS/heightInches; //also incorporates activity level?
    }
    //only called if userID already exists
    public void updateUser(int userIDPassed, String firstNamePassed, String lastNamePassed, int agePassed, int heightPassed, float weightPassed, String cityPassed, String countryPassed, Enum sexPassed, Bitmap profilePicPassed) {
        if(userID != userIDPassed) {
            return; //throw error or something?
        }
        firstName = firstNamePassed;
        lastName = lastNamePassed;
        age = agePassed;
        heightInches = heightPassed;
        weightLBS = weightPassed;
        city = cityPassed;
        country = countryPassed;
        sex = sexPassed;
        profilePic = profilePicPassed;
    }
    //called in fitnessGoals Module
    public void updateWeightChangeGoal(float changeGoal) {
        weightChangeGoal = changeGoal;
    }
    //called in fitnessGoals Module
    public void updateActivityLevel(Enum activityLevelPassed) {
        activityLevel = activityLevelPassed;
    }
    //called in fitnessGoals Module
    public void updateFitnessGoal(Enum fitnessGoalPassed) {
        fitnessGoal = fitnessGoalPassed;
    }
}