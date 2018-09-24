package com.example.masonwest.lifestyle_app;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class User {
    private String sex;
    private String activityLevel;
    private int userID;
    private String firstName;
    private String lastName;
    private int age;
    private int heightInches;
    private double weightLBS;
    private String city;
    private String country;
    private Bitmap profilePic;
    private double BMR;
    private double BMI;
    private double weightChangeGoal; //positive or negative based on fitness goal?
    private double recommendedDailyCalorieIntake;

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
    //call before sending BMI to BMI fragment
    public void calculateBMI() {
        if(weightLBS > 0 && heightInches > 0) {
            //multiply by 703 for english system vs metric system
            BMI = (weightLBS / (heightInches * heightInches)) * 703;
        } else {
            return; //if 0/null notify in fragment?
        }
    }
    //should be called to calculate base BMR and also desiredBMR
    public static double calculateBMR(double currentWeightPassed, int heightPassed, int agePassed, String sexPassed) {
        if(currentWeightPassed > 0 && heightPassed > 0 && agePassed > 0) {
            if (sexPassed.equals("Male")) {
                return 655 + (4.35 * currentWeightPassed) + (4.7 * heightPassed) - (4.7 * agePassed);
            } else {
                return 66 + (6.23 * currentWeightPassed) + (12.7 * heightPassed) - (6.8 * agePassed);
            }
        }
        else {
            return 0; //if 0/null notify in fragment?
        }
    }

    public static double calculateDailyRecommendedCalorieIntake(double BMRPassed, String activityLevelPassed, double weightGoalPassed) {
        double recommendedCalorieIntake = 0;
        switch (activityLevelPassed) {
            case "Sedentary": recommendedCalorieIntake = BMRPassed * 1.2;
                break;
            case "Lightly Active": recommendedCalorieIntake = BMRPassed * 1.375;
                break;
            case "Moderately Active": recommendedCalorieIntake = BMRPassed * 1.55;
                break;
            case "Very Active": recommendedCalorieIntake = BMRPassed * 1.725;
                break;
            case "Extra Active": recommendedCalorieIntake = BMRPassed * 1.9;
                break;
        }
        return recommendedCalorieIntake + (500 * weightGoalPassed);
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
    public double getWeightChangeGoal() {
        return weightChangeGoal;
    }
    public String getSex() {
        return sex;
    }
    public void updateWeeklyGainLoss(double changeGoal) {
        weightChangeGoal = changeGoal;
    }
    public void updateActivityLevel(String activityLevelPassed) {
        activityLevel = activityLevelPassed;
    }

    public double getDailyRecommendedCalorieIntake() {
        return recommendedDailyCalorieIntake;
    }

    public Bitmap getProfilePic() {
        return profilePic;
    }
    public String getName() {
        return firstName + " " + lastName;
    }
    public double getBMI() {
        return BMI;
    }
    public int getUserID() {
        return userID;
    }
//    public static User getUser() {
//
//    }
}
