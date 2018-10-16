package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<List<User>> mAllUsers;
    private MutableLiveData<User> mUser;
    private MutableLiveData<WeatherData> jsonData;
    private static UserRepository mUserRepository;

    public UserViewModel(Application application) {
        super(application);
        if(mUserRepository == null) {
            mUserRepository = new UserRepository(application);
        }
        mUser = mUserRepository.getUser();
        jsonData = mUserRepository.getData();
    }

    // WEATHERDATA Tools
    public void setLocation(String location){
        mUserRepository.setLocation(location);
    }
    public LiveData<WeatherData> getData(){
        return jsonData;
    }

    public String getFirstName() {
        return mUserRepository.getFirstName();
    }
    public void setFirstName(String firstName) {
        mUserRepository.setFirstName(firstName);
    }
    public String getLastName() {
        return mUserRepository.getLastName();
    }
    public void setLastName(String lastName) {
        mUserRepository.setLastName(lastName);
    }
    public String getFullName() {
        return mUserRepository.getFullName();
    }
    public void setFullName(String fName, String lName) { mUserRepository.setFullName(fName, lName); }
    public int getAge() {
        return mUserRepository.getAge();
    }
    public void setAge(int age) {
        mUserRepository.setAge(age);
    }
    public String getSex() {
        return mUserRepository.getSex();
    }
    public void setSex(String sex) {
        mUserRepository.setSex(sex);
    }
    public int getHeight() {
        return mUserRepository.getHeight();
    }
    public void setHeight(int height) {
        mUserRepository.setHeight(height);
    }
    public int getWeight() {
        return mUserRepository.getWeight();
    }
    public void setWeight(int weight) {
        mUserRepository.setWeight(weight);
    }
    public double getBMI() {
        return mUserRepository.getBMI();
    }
    public void setBMI(double bmi) {
        mUserRepository.setBMI(bmi);
    }
    public double getBMR() {
        return mUserRepository.getBMR();
    }
    public void setBMR(double bmr) {
        mUserRepository.setBMR(bmr);
    }
    public String getActivityLevel() {
        return mUserRepository.getActivityLevel();
    }
    public void setActivityLevel(String activityLevel) {
        mUserRepository.setActivityLevel(activityLevel);
    }
    public double getDailyRecommendedCalorieIntake() {
        return mUserRepository.getDailyRecommendedCalorieIntake();
    }
    public void setDailyRecommendedCalorieIntake(double calorieIntake) {
        mUserRepository.setDailyRecommendedCalorieIntake(calorieIntake);
    }
    public String getCity() {
        return mUserRepository.getCity();
    }
    public void setCity(String city) {
        mUserRepository.setCity(city);
    }
    public String getCountry() {
        return mUserRepository.getCountry();
    }
    public void setCountry(String country) {
        mUserRepository.setCountry(country);
    }
    public String getLocation() {
        return this.getCity() + "," + this.getCountry();
    }
    public double getWeightChangeGoal() {
        return mUserRepository.getWeightChangeGoal();
    }
    public void setWeightChangeGoal(double weightChangeGoal) {
        mUserRepository.setWeightChangeGoal(weightChangeGoal);
    }
    public Bitmap getProfilePic() {
        return mUserRepository.getProfilePic();
    }
    public void setProfilePic(Bitmap profilePic) {
        mUserRepository.setProfilePic(profilePic);
    }

    public void delete(User user) {
//        mUserRepository.delete(user);
    }

    public void insert(User user) { mUserRepository.insert(user); }

    public MutableLiveData<User> getUser() {
        return mUser;
    }
    public void setUser(User user) {
        mUserRepository.setUser(user);
    }

    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

}