package com.example.masonwest.lifestyle_app;

import android.os.AsyncTask;

/**
 * Used only by the call in the UserRepository to get the number of users in the data base
 * Method is called getNumberOfProfilesInDatabase()
 * @param <T>
 */
public abstract class VoidAsyncTask<T> extends AsyncTask<Void, Void, T> {
    public void execute() { super.execute(); }
}
