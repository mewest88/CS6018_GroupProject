package com.example.masonwest.lifestyle_app;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user) ;

    @Query("DELETE from user_table")
    void deleteAll() ;

    @Query("SELECT * from user_table")
    List<User> getAllUsers() ;

}