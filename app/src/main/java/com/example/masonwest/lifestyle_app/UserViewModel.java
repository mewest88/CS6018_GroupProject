package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private MutableLiveData<User> mUser;
    private MutableLiveData<WeatherData> jsonData;
    private static UserRepository mUserRepository;

    public UserViewModel(Application application) {
        super(application);
        mUserRepository = UserRepository.getInstance(application);
        mUser = mUserRepository.getUser();
        jsonData = mUserRepository.getData();
    }

    public void insert(User user) { mUserRepository.insert(user); }

    public void update(User user) { mUserRepository.update(user);}

    public void dumpInDB(User user) {
//        User user = mUser.getValue();
        update(user);
//        mUser = mUserRepository.getUser();
    }

    public LiveData<User> getUser() { return mUser; }

    public void setUser(User user) { mUserRepository.setUser(user); }

    public VoidAsyncTask getNumberOfUserInDatabase(){ return mUserRepository.getNumberOfProfilesInDatabase(); }

    // WEATHERDATA Tools
    public void setLocation(String location){
        mUserRepository.setLocation(location);
    }

    public LiveData<WeatherData> getData(){
        return jsonData;
    }
}