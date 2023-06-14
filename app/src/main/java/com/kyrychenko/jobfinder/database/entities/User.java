package com.kyrychenko.jobfinder.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    @ColumnInfo(name = "is_admin")
    private Boolean isAdmin;

    @NonNull
    private String login;

    @NonNull
    private String password;

    public User(Integer id, Boolean isAdmin, @NonNull String login, @NonNull String password) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    @NonNull
    public String getPassword() {
        return password;
    }
}
