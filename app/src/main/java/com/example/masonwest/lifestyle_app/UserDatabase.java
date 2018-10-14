package com.example.masonwest.lifestyle_app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao() ;

    private static volatile UserDatabase INSTANCE;

    // Making it a Singleton
    static UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                }
            }
        }
        return INSTANCE;
    }


}
