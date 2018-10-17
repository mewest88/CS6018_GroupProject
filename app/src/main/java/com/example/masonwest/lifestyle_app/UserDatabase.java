package com.example.masonwest.lifestyle_app;

    import android.arch.persistence.room.Database;
    import android.arch.persistence.room.Room;
    import android.arch.persistence.room.RoomDatabase;
    import android.content.Context;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static volatile UserDatabase INSTANCE;

    // Making it a Singleton
    static UserDatabase getDatabase(final Context context) {
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
}
