package com.kyrychenko.jobfinder.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.kyrychenko.jobfinder.MainViewModel;
import com.kyrychenko.jobfinder.R;
import com.kyrychenko.jobfinder.activities.fragments.JobsListFragment;

//Activity-container for fragments and ViewModel
public class MainActivity extends AppCompatActivity {
    private MaterialButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Activity activity = this;
        logoutButton = (MaterialButton) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, InitActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments_container, new JobsListFragment())
                .commit();
    }

    public static MainViewModel obtainViewModel(FragmentActivity activity){
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication());
        return  new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }
}