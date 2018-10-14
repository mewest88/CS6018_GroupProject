package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class UserRepository {
    private MutableLiveData<User> mUser = new MutableLiveData<>();
    private User temp = new User(-1);
//    private MutableLiveData<List<User>> mAllUsers;
//    private UserDao mUserDao ;

    public UserRepository(Application application) {
//        UserDatabase db = UserDatabase.getDatabase(application);
//        mUserDao = db.userDao();
//        mAllUsers = mUserDao.getAllUsers();
        mUser.setValue(temp);
        updateUser();
        loadData();
    }

    //    public void insert(User user) {
//        new insertAsyncTask(mUserDao).execute(user) ;
//    }

    MutableLiveData<User> getUser() {
        return mUser;
    }
    public void setUser(User user) {
        temp = user;
        updateUser();
    }
//    public void newUser(User user) {
//        temp = user;
//        updateUser();
//    }
    public String getFirstName() {
        if(mUser.getValue() != null) {
            return mUser.getValue().getFirstName();
        }
        return "loser";
    }
    public void setFirstName(String firstName) {
        mUser.getValue().setFirstName(firstName);
    }
    public String getLastName() {
        if(mUser.getValue() != null) {
            return mUser.getValue().getLastName();
        }
        return "last name";
    }
    public void setLastName(String lastName) {
        mUser.getValue().setLastName(lastName);
    }
    public String getFullName() {
        String first = this.getFirstName();
        String last = this.getLastName();
        return first + " " + last;
    }
    public int getAge() {
        User tempUser = temp;
        User testUser = mUser.getValue();
        return mUser.getValue().getAge();
    }
    public void setAge(int age) {
        mUser.getValue().setAge(age);
    }
    public String getSex() {
        return mUser.getValue().getSex();
    }
    public void setSex(String sex) {
        mUser.getValue().setSex(sex);
    }
    public int getHeight() {
        return mUser.getValue().getHeight();
    }
    public void setHeight(int height) {
        mUser.getValue().setHeight(height);
    }
    public int getWeight() {
        return mUser.getValue().getWeight();
    }
    public void setWeight(int weight) {
        mUser.getValue().setWeight(weight);
    }
    public double getBMI() {
        return mUser.getValue().getBMI();
    }
    public void setBMI(double bmi) {
        mUser.getValue().setBMI(bmi);
    }
    public double getBMR() {
        return mUser.getValue().getBMR();
    }
    public void setBMR(double bmr) {
        mUser.getValue().setBMR(bmr);
    }
    public String getActivityLevel() {
        return mUser.getValue().getActivityLevel();
    }
    public void setActivityLevel(String activityLevel) {
        mUser.getValue().setActivityLevel(activityLevel);
    }
    public double getDailyRecommendedCalorieIntake() {
        return mUser.getValue().getDailyRecommendedCalorieIntake();
    }
    public void setDailyRecommendedCalorieIntake(double calorieIntake) {
        mUser.getValue().setDailyRecommendedCalorieIntake(calorieIntake);
    }
    public String getCity() {
        return mUser.getValue().getCity();
    }
    public void setCity(String city) {
        mUser.getValue().setCity(city);
    }
    public String getCountry() {
        return mUser.getValue().getCountry();
    }
    public void setCountry(String country) {
        mUser.getValue().setCountry(country);
    }
    public String getLocation() {
        return getCity() + "," + getCountry();
    }
    public double getWeightChangeGoal() {
        return mUser.getValue().getWeightChangeGoal();
    }
    public void setWeightChangeGoal(double weightChangeGoal) {
        mUser.getValue().setWeightChangeGoal(weightChangeGoal);
    }
    public Bitmap getProfilePic() {
        return mUser.getValue().getProfilePic();
    }
    public void setProfilePic(Bitmap profilePic) {
        mUser.getValue().setProfilePic(profilePic);
    }
//    MutableLiveData<List<User>> getAllUsers() {
//        return mAllUsers;
//    }

    private void updateUser() {
        new AsyncTask<User, Void, User>() {
            @Override
            protected User doInBackground(User... user) {
                User tempUser = null;
                if(user[0] != null) {
                    tempUser = user[0];
                }
//                temp.setFirstName("Christopher");
//                temp.setLastName("bitch");
                return tempUser;
            }

            @Override
            protected void onPostExecute(User user) {
                if(user != null) {
                    mUser.setValue(user);
                }
            }
        }.execute(temp);
    }



    // AsyncTask class
//    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
//
//        private UserDao mAsyncTaskDao;
//
//        insertAsyncTask(UserDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final User... params) {
//            mAsyncTaskDao.insert(params[0]);
//            return null;
//        }
//    }

    /*
    BEGINNING OF USED FOR WEATHER TOOLS ---------------------------------------------------------
    */
    private final MutableLiveData<WeatherData> jsonData = new MutableLiveData<WeatherData>();
    private String mLocation;

//    WeatherRepository(Application application){ //redone in the UserRepository constuctor above
//        loadData();
//    }

    public void setLocation(String location){
        mLocation = location;
        loadData();
    }

    public MutableLiveData<WeatherData> getData() {
        return jsonData;
    }

    private void loadData() {
        new AsyncTask<String,Void,String>() {
            @Override
            protected String doInBackground(String... strings) {
                String location = strings[0];
                URL weatherDataURL = null;
                String retrievedJsonData = null;
                if(location!=null) {
                    weatherDataURL = NetworkUtils.buildURLFromString(location);
                    try {
                        retrievedJsonData = NetworkUtils.getDataFromURL(weatherDataURL);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return retrievedJsonData;
            }

            @Override
            protected void onPostExecute(String s) {
                if(s != null) {
                    try {
                        jsonData.setValue(JSONWeatherUtils.getWeatherData(s));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute(mLocation);
    }
    /*
    END OF WEATHER TOOLS - - - - - - - - - - - ----------------------------------------------
    */
}
