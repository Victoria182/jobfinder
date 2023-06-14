package com.kyrychenko.jobfinder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kyrychenko.jobfinder.R;
import com.kyrychenko.jobfinder.activities.fragments.InitActivityMainFragment;

//Activity-container for fragments
public class InitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.init_container, new InitActivityMainFragment())
                .commit();
    }
}