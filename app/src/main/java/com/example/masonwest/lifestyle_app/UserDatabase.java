package com.example.masonwest.lifestyle_app;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.File;

@Database(entities = {User.class, LocationData.class, CurrentCondition.class}, version = 3, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract LocationDao locationDao();
    public abstract CurrentConditionDao conditionDao();

    private static volatile UserDatabase INSTANCE;

    // Making it a Singleton
    public static UserDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "user_database")
                            .fallbackToDestructiveMigration() // Wipes and rebuilds instead of migrating if no Migration object.
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mDao;

        PopulateDbAsync(UserDatabase db) {
            mDao = db.userDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            mDao.deleteAll();
////            User tempUser = new User(100, "mason", "west", "Male", "London", "GB", "Sedentary", 30, 72, 160, 24.0, 24.0, );
//            User tempUser = new User(100);
//            mDao.insert(tempUser);
            return null;
        }
    }
//    public User(int userID, String firstName, String lastName, String fullName, String sex, String city, String country,
//                String activityLevel, int age, int heightInches, int weightLBS, double BMR, double BMI,
//                double weightChangeGoal, double recommendedDailyCalorieIntake, byte[] profileImageData) {
//    }

}
