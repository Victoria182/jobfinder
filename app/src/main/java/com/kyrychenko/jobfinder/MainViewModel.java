package com.kyrychenko.jobfinder;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kyrychenko.jobfinder.database.DatabaseRepository;
import com.kyrychenko.jobfinder.database.entities.Job;
import com.kyrychenko.jobfinder.database.entities.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

//ViewModel to hold observable LiveData objects and perform operations
public class MainViewModel extends AndroidViewModel {
    private DatabaseRepository repository;
    private LiveData<List<Job>> allJobs;

    public MainViewModel(Application application){
        super(application);
        repository = new DatabaseRepository(application);
        allJobs = repository.getAllJobs();
    }

    public LiveData<List<Job>> getAllJobs(){
        return allJobs;
    }

    public void deleteJob(Job job){
        repository.deleteJob(job);
    }

    public void insertJob(Job job){
        repository.insertJob(job);
    }

    public void insertUser(User user){
        repository.insertUser(user);
    }

    public User findUserByLogin(String login){
        try {
            return repository.findByLogin(login);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
