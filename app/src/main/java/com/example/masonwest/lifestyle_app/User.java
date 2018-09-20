package com.example.masonwest.lifestyle_app;

import android.graphics.Bitmap;

public class User {
    private String sex;
    private String fitnessGoal;
    private String activityLevel;
    private int userID;
    private String firstName;
    private String lastName;
    private int age;
    private int heightInches;
    private float weightLBS;
    private String city;
    private String country;
    private Bitmap profilePic;
    private double BMR;
    private double desiredBMR;
    private float BMI;
    private String overallGoal; //overall weight goal or gain/loss per week goal
    private float weightChangeGoal; //positive or negative based on fitness goal?
    private float desiredWeight; //potentially not used?
    private double recommendedCalorieIntake;

    User(int userIDPassed, String firstNamePassed, String lastNamePassed, int agePassed, int heightPassed, float weightPassed, String cityPassed, String countryPassed, Bitmap profilePicPassed, String sexPassed) {
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
    }
    //call in BMI fragment
    public void calculateBMI() {
        if(weightLBS > 0 && heightInches > 0) {
            //multiply by 703 for english system vs metric system
            BMI = 703*weightLBS/(heightInches*heightInches);
        } else {
            return; //if 0/null notify in fragment?
        }
    }
    //should be called to calculate base BMR and also desiredBMR
    public double calculateBMR(float desiredWeight) {
        if(desiredWeight > 0 && heightInches > 0 && age > 0) {
            if (sex == "Male") {
                return 655 + (4.35 * desiredWeight) + (4.7 * heightInches) - (4.7 * age);
            } else {
                return 66 + (6.23 * desiredWeight) + (12.7 * heightInches) - (6.8 * age);
            }
        }
        else {
            return 0; //if 0/null notify in fragment?
        }
    }

    public void calculateRecommendedCalorieIntake() {
        switch (activityLevel) {
            case "Sedentary": recommendedCalorieIntake = BMR * 1.2;
            break;
            case "Lightly Active": recommendedCalorieIntake = BMR * 1.375;
            break;
            case "Moderately Active": recommendedCalorieIntake = BMR * 1.55;
            break;
            case "Very Active": recommendedCalorieIntake = BMR * 1.725;
            break;
            case "Extra Active": recommendedCalorieIntake = BMR * 1.9;
            break;
        }
    }
    //call if they have a weekly goal
    public void adjustRecommendedCalorieIntakeByWeeklyGoal(float weeklyWeightChangeGoal) {
        //lose/gain 1 pound: 500 calorie deficit per week
        recommendedCalorieIntake += 500 * weeklyWeightChangeGoal; //check if <1200 in fragment?
    }
    public void adjustRecommendedCalorieIntakeByTotalGoal(float totalWeightChangeGoal) {
        //lose/gain 1 pound: 500 calorie deficit per week over 12 week horizon
        recommendedCalorieIntake += 500 * totalWeightChangeGoal / 12;
    }

    //only called if userID already exists
    public void updateUser(int userIDPassed, String firstNamePassed, String lastNamePassed, int agePassed, int heightPassed, float weightPassed, String cityPassed, String countryPassed, Bitmap profilePicPassed, String sexPassed) {
        //error checking can be done on data entry
        //        if(userID != userIDPassed) {
        //            return;
        //        }
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

    //these called in fitnessGoals Module
    public void updateOverallGoal(String goal) {
        overallGoal = goal;
    }
    public void updateWeightChangeGoal(float changeGoal) {
        weightChangeGoal = changeGoal;
    }
    public void updateActivityLevel(String activityLevelPassed) {
        activityLevel = activityLevelPassed;
    }
    public void updateFitnessGoal(String fitnessGoalPassed) {
        fitnessGoal = fitnessGoalPassed;
    }
}
