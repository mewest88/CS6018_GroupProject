package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class UserRepository {
    private final MutableLiveData<User> userData = new MutableLiveData<>() ;
    private MutableLiveData<List<User>> mAllUsers;
//    private UserDao mUserDao ;

    UserRepository(Application application) {
//        UserDatabase db = UserDatabase.getDatabase(application);
//        mUserDao = db.userDao();
//        mAllUsers = mUserDao.getAllUsers();
        loadData();
    }

    //    public void insert(User user) {
//        new insertAsyncTask(mUserDao).execute(user) ;
//    }

    MutableLiveData<List<User>> getAllUsers() { return mAllUsers; }

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

    private void loadData(){
        new AsyncTask<String,Void,String>(){
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
