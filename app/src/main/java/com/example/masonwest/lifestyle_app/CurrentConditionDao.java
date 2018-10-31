package com.example.masonwest.lifestyle_app;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface CurrentConditionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentCondition cond);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(CurrentCondition cond);

    @Query("SELECT * from currentcondition_table LIMIT 1")//LIMIT 1
    CurrentCondition getCondition();

//    @Query("SELECT Count(*) from currentcondition_table")//LIMIT 1
//    int getNumberOfUserInDatabase();

    @Query("DELETE from currentcondition_table")
    void deleteAll();

    //    LiveData<List<User>> getAllUsers();
    //    @Query("SELECT * from user_table ORDER BY userID ASC")
}

