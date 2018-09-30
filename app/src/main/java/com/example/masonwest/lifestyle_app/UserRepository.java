package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

public class UserRepository {
    private final MutableLiveData<User> userData = new MutableLiveData<>() ;


    UserRepository(Application application) {
        loadData() ;
    }

    private void loadData() {

    }
}
