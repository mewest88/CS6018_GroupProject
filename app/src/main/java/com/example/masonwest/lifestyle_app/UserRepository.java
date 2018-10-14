package com.example.masonwest.lifestyle_app;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {
    private final MutableLiveData<User> userData = new MutableLiveData<>() ;
    private MutableLiveData<List<User>> mAllUsers;
    private UserDao mUserDao ;

    UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAllUsers();
    }

    public void insert(User user) {
        new insertAsyncTask(mUserDao).execute(user) ;
    }

    MutableLiveData<List<User>> getAllUsers() { return mAllUsers; }



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
}
