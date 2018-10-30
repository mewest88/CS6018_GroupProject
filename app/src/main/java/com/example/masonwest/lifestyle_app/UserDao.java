package com.example.masonwest.lifestyle_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Query("SELECT * from user_table LIMIT 1")//LIMIT 1
    User getUser();

    @Query("SELECT Count(*) from user_table")//LIMIT 1
    int getNumberOfUserInDatabase();

    @Query("DELETE from user_table")
    void deleteAll();

    //    LiveData<List<User>> getAllUsers();
    //    @Query("SELECT * from user_table ORDER BY userID ASC")
}

