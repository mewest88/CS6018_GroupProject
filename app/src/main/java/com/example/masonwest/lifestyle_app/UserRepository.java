package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

public class UserRepository {
    private final MutableLiveData<User> userData = new MutableLiveData<>() ;
    private MutableLiveData<List<User>> mAllUsers;

    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAllWords();
    }

    MutableLiveData<List<User>> getAllUsers() { return mAllUsers; }
    private void loadData() {

    }
}
