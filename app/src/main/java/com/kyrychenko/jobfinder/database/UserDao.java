package com.kyrychenko.jobfinder.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.kyrychenko.jobfinder.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM users WHERE login=:log")
    User findByLogin(String log);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
