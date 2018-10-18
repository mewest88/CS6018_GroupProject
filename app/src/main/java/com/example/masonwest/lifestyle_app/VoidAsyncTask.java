package com.example.masonwest.lifestyle_app;

import android.os.AsyncTask;

public abstract class VoidAsyncTask<T> extends AsyncTask<Void, Void, T> {
    public void execute() { super.execute(); }
}
