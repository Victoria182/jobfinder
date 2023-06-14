package com.kyrychenko.jobfinder.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kyrychenko.jobfinder.database.entities.Job;
import com.kyrychenko.jobfinder.database.entities.User;

//General description of database
@androidx.room.Database(entities = {User.class, Job.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private final static String DATABASE_NAME = "db.sqlite";

    public abstract UserDao userDao();
    public abstract JobDao jobDao();

    //Pattern Singletone
    private static Database INSTANCE;

    public static Database getDatabase(final Context context){
        //Double concurrency lock
        if(INSTANCE == null){
            synchronized (Database.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            Database.class,
                            DATABASE_NAME
                    )
                            .createFromAsset(DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
