package com.example.masonwest.lifestyle_app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user_table")
public class User implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_ID")

    private String firstName;
    private String lastName;
    private String fullName;
    private String sex;
    private String activityLevel;
    private String city;
    private String country;
    private int userID;
    private int age;
    private int heightInches;
    private int weightLBS;
    private double BMR;
    private double BMI;
    private double weightChangeGoal; //positive or negative based on fitness goal
    private double recommendedDailyCalorieIntake;
    private Bitmap profilePic;

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        //Call the private constructor
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        fullName = firstName + " " + lastName;
        sex = in.readString();
        activityLevel = in.readString();
        city = in.readString();
        country = in.readString();

        userID = in.readInt();
        age = in.readInt();
        heightInches = in.readInt();
        weightLBS = in.readInt();

        BMR = in.readDouble();
        BMI = in.readDouble();
        weightChangeGoal = in.readDouble();
        recommendedDailyCalorieIntake = in.readDouble();

        profilePic = in.readParcelable(null);
    }
    public User(int userIDPassed) {
        userID = userIDPassed;
    }

    //call before sending BMI to BMI fragment
    public static double calculateBMI(int weight, int height) {
        double heightSquared = height * height;
        double unconverted = weight / heightSquared;
        double converted = unconverted * 703;
        return converted;
    }
    //should be called to calculate base BMR
    public static double calculateBMR(double currentWeightPassed, int heightPassed, int agePassed, String sexPassed) {
        double weightMetric = currentWeightPassed / 2.2;
        double heightMetric = heightPassed * 2.54;
        if(currentWeightPassed > 0 && heightPassed > 0 && agePassed > 0) {
            if (sexPassed.equals("Male")) {
                return 5 + (10 * weightMetric) + (6.25 * heightMetric) - (5 * agePassed);
            } else {
                return -161 + (10 * weightMetric) + (6.25 * heightMetric) - (5 * agePassed);
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
    public void updateUser(Bundle userData) {
        profilePic = userData.getParcelable("userProfilePic");
        firstName = userData.getString("userFirstName");
        lastName = userData.getString("userLastName");
        fullName = firstName + " " + lastName;
        sex = userData.getString("userSex");
        activityLevel = userData.getString("userActivityLevel");
        city = userData.getString("userCity");
        country = userData.getString("userCountry");

        userID = userData.getInt("userID");
        age = userData.getInt("userAge");
        heightInches = userData.getInt("userHeight");
        weightLBS = userData.getInt("userWeight");

        BMR = userData.getDouble("userBMR");
        BMI = userData.getDouble("userBMI");
        weightChangeGoal = userData.getDouble("userGoal");
        recommendedDailyCalorieIntake = userData.getDouble("userCalories");
    }

    //these called in fitnessGoals Module
    public double getWeightChangeGoal() {
        return weightChangeGoal;
    }
    public void setWeightChangeGoal(double weightChangeGoalPassed) {
        weightChangeGoal = weightChangeGoalPassed;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sexPassed) {
        sex = sexPassed;
    }
    public void setProfilePic(Bitmap profilePicPassed) {
        profilePic = profilePicPassed;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstNamePassed) {
        firstName = firstNamePassed;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastNamePassed) {
        lastName = lastNamePassed;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int agePassed) {
        age = agePassed;
    }
    public int getWeight() {
        return weightLBS;
    }
    public void setWeight(int weightPassed) {
        weightLBS = weightPassed;
    }
    public int getHeight() {
        return heightInches;
    }
    public void setHeight(int heightPassed) {
        heightInches = heightPassed;
    }
    public String getActivityLevel() {
        return activityLevel;
    }
    public void setActivityLevel(String activityLevelPassed) {
        activityLevel = activityLevelPassed;
    }
    public double getDailyRecommendedCalorieIntake() {
        return recommendedDailyCalorieIntake;
    }
    public void setDailyRecommendedCalorieIntake(double calorieIntake) {
        recommendedDailyCalorieIntake = calorieIntake;
    }
    public Bitmap getProfilePic() {
        return profilePic;
    }
    public String getFullName() {
        return fullName;
    }
    public double getBMI() {
        return BMI;
    }
    public void setBMI(double bmiPassed) {
        BMI = bmiPassed;
    }
    public double getBMR() {
        return BMR;
    }
    public void setBMR(double passedBMR) {
        BMR = passedBMR;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String cityPassed) {
        city = cityPassed;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String countryPassed) {
        country = countryPassed;
    }
    public String getLocation() { return city + "," + country; }
    public int getUserID() {
        return userID;
    }
    public User getUser() {
        //something to check user id and return the right user?
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(sex);
        dest.writeString(activityLevel);
        dest.writeString(city);
        dest.writeString(country);

        dest.writeInt(userID);
        dest.writeInt(age);
        dest.writeInt(heightInches);
        dest.writeInt(weightLBS);

        dest.writeDouble(BMR);
        dest.writeDouble(BMI);
        dest.writeDouble(weightChangeGoal);
        dest.writeDouble(recommendedDailyCalorieIntake);

        dest.writeParcelable(profilePic, flags);
    }
}
