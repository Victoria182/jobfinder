package com.kyrychenko.jobfinder.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.kyrychenko.jobfinder.database.entities.Job;

import java.util.List;

@Dao
public interface JobDao {
    @Query("SELECT * FROM jobs")
    LiveData<List<Job>> getAll();

    @Insert
    void insert(Job job);

    @Delete
    void delete(Job job);
}
