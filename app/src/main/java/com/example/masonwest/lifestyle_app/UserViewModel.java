package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<List<User>> mAllUsers;
    private UserRepository mUserRepository;

    public UserViewModel(Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
        mAllUsers = mUserRepository.getAllUsers();
    }

    public void insert(User user) { mUserRepository.insert(user); }

    MutableLiveData<List<User>> getAllUsers() { return mAllUsers; }


}
