package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {
    private LiveData<User> user ;
    private UserRepository mUserRepository ;

    public UserViewModel(Application application) {
        super(application) ;
        mUserRepository = new UserRepository(application) ;
    }

    public LiveData<User> getData() {
        return this.user ;
    }

}
