package com.kyrychenko.jobfinder.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kyrychenko.jobfinder.database.entities.Job;
import com.kyrychenko.jobfinder.database.entities.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

//Repository describing different operations on database
public class DatabaseRepository {
    private UserDao userDao;
    private JobDao jobDao;
    private LiveData<List<Job>> allJobs;

    public DatabaseRepository(Application application){
        Database db = Database.getDatabase(application);
        jobDao = db.jobDao();
        allJobs = jobDao.getAll();
        userDao = db.userDao();
    }

    public LiveData<List<Job>> getAllJobs(){
        return allJobs;
    }

    public User findByLogin(String login) throws ExecutionException, InterruptedException {
        return new findByLoginAsyncTask(login).execute(userDao).get();
    }

    public void deleteJob(Job job){
        new deleteJobAsyncTask(job).execute(jobDao);
    }

    public void insertUser(User user){
        new insertUserAsyncTask(user).execute(userDao);
    }

    public void insertJob(Job job){
        new insertJobAsyncTask(job).execute(jobDao);
    }

    //Using asynchronous tasks to perform database queries on the separate, not UI, threads
    private static class insertUserAsyncTask extends AsyncTask<UserDao, Void, Void>{
        private User user;
        public insertUserAsyncTask(User user){
            this.user = user;
        }
        @Override
        protected Void doInBackground(UserDao... userDaos) {
            userDaos[0].insert(user);
            return null;
        }
    }

    private static class insertJobAsyncTask extends AsyncTask<JobDao, Void, Void>{
        private Job job;
        public insertJobAsyncTask(Job job){
            this.job = job;
        }

        @Override
        protected Void doInBackground(JobDao... jobDaos) {
            jobDaos[0].insert(job);
            return null;
        }
    }

    private static class deleteJobAsyncTask extends  AsyncTask<JobDao, Void, Void>{
        private Job job;
        public deleteJobAsyncTask(Job job){
            this.job = job;
        }

        @Override
        protected Void doInBackground(JobDao... jobDaos) {
            jobDaos[0].delete(job);
            return null;
        }
    }

    private static class findByLoginAsyncTask extends AsyncTask<UserDao, Void, User>{
        private String login;

        public findByLoginAsyncTask(String login) {
            this.login = login;
        }

        @Override
        protected User doInBackground(UserDao... userDaos) {
            return userDaos[0].findByLogin(login);
        }
    }
}
