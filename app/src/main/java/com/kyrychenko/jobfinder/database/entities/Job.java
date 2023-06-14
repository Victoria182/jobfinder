package com.kyrychenko.jobfinder.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Database entity class describing the future and/or existing tables
// ORM technology
@Entity(tableName = "jobs")
public class Job {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private Integer salary;

    public Job(Integer id, @NonNull String title, @NonNull String description, Integer salary) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public Integer getSalary() {
        return salary;
    }
}
