package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class UserRepository {

    // SINGLETON - static variable single_instance of type UserRepository
    private static UserRepository single_instance = null;

    //    private MutableLiveData<User> mUser = new MutableLiveData<>();
    private static final MutableLiveData<User> mUser = new MutableLiveData<>();
    private UserDao mUserDao;

    // static method to create instance of Singleton class
    public static UserRepository getInstance(Application application)
    {
        if (single_instance == null) {
            single_instance = new UserRepository(application);
        }

        return single_instance;
    }

    private UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        mUserDao = db.userDao();
        loadData();
    }

    public void insert(User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }

    public void setUser(User user) {
        mUser.setValue(user);
    }

    // AsyncTask class
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void update(User user) {
        new updateAsyncTask(mUserDao, mUser).execute(user);
    }

    private static class updateAsyncTask extends AsyncTask<User, Void, User>{
        private UserDao mAsyncTaskDao;
        private MutableLiveData<User> mProfileData;

        updateAsyncTask(UserDao dao, MutableLiveData<User> profile){
            this.mAsyncTaskDao = dao;
            this.mProfileData = profile;
        }

        @Override
        protected User doInBackground(final User... user) {
            mAsyncTaskDao.update(user[0]);
            return user[0];
        }

        @Override
        protected void onPostExecute(User profile) {
            //mProfileData.setValue(profile);
            mUser.setValue(profile);
//            uploadWithTransferUtility(mAppContext);
        }
    }

    public MutableLiveData<User> getUser() {
        return mUser;
    }

//    public void setUser(User user) {
//        mUser.setValue(user);
//    }

    /*
    BEGINNING OF USED FOR WEATHER TOOLS ---------------------------------------------------------
    */
    private final MutableLiveData<WeatherData> jsonData = new MutableLiveData<WeatherData>();
    private String mLocation;

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

    // Gets the number of rows in the table
    public VoidAsyncTask getNumberOfProfilesInDatabase(){
        rowsInDatabaseTask task = new rowsInDatabaseTask(mUserDao);
        return task;
    }

    // For getting the number of rows/entries in the database
    private static class rowsInDatabaseTask extends VoidAsyncTask<Integer> {

        private UserDao mAsyncTaskDao;
        private int result;

        rowsInDatabaseTask(UserDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            result =  mAsyncTaskDao.getNumberOfUserInDatabase();
            return result;
        }

        protected int onPostExecute(int result) {
            return result;
        }

        public int getResult(){
            return result;
        }

    }


    public static File getDatabaseFile(Context context) {
        String backupDBPath = UserDatabase.getDatabase(context).getOpenHelper().getWritableDatabase().getPath();
        File dbPath = new File(backupDBPath) ;
        if (dbPath.exists()) {
            Log.d("UserRepository", "file worked!!") ;
        }
        return dbPath ;
    }
}
