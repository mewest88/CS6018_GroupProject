package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE from user_table WHERE userID = :userID")
    void delete(int userID);

    @Query("DELETE from user_table")
    void deleteAll();

    @Query("SELECT * from user_table ORDER BY userID ASC")
    LiveData<List<User>> getAllUsers();

//    @Query("SELECT * from user_table WHERE userID = :userID")
//    LiveData<List<User>> getUser(int userID);

    @Query("SELECT * from user_table LIMIT 1")
    LiveData<User> getUser();

}
