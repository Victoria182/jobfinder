package com.kyrychenko.jobfinder.activities.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.kyrychenko.jobfinder.MainViewModel;
import com.kyrychenko.jobfinder.R;
import com.kyrychenko.jobfinder.activities.MainActivity;
import com.kyrychenko.jobfinder.database.entities.User;

public class InitActivityLoginFragment extends Fragment {
    private MaterialButton loginButton;

    //Model for accessing LiveData values through observer
    private MainViewModel viewModel;

    private EditText loginEditText, passwordEditField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initializing fragment view
        View view = inflater.inflate(R.layout.fragment_init_activity_login, container, false);

        //Obtaining general ViewModel from single activity
        viewModel = MainActivity.obtainViewModel(getActivity());

        loginButton = (MaterialButton) view.findViewById(R.id.login_button);

        loginEditText = (EditText) view.findViewById(R.id.login_field);
        passwordEditField = (EditText) view.findViewById(R.id.password_field);

        //Defining final variable to access activity inside listener method
        final Activity currentActivity = getActivity();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting values from fields with converting
                final String login = loginEditText.getText().toString();
                final String password = passwordEditField.getText().toString();

                //Trying to find user with such login
                User user = viewModel.findUserByLogin(login);
                if(user != null){
                    //If user found..
                    if(user.getPassword().equals(password)){
                        //If password is valid
                        // -> go to the different activity
                        Intent intent = new Intent(currentActivity, MainActivity.class);
                        //Passing value describing user role to next activity
                        intent.putExtra("IS_ADMIN", user.isAdmin());
                        startActivity(intent);
                        //Finishing current activity to disable pop backstack on back button click
                        currentActivity.finish();
                    }else {
                        //Showing error message
                        Toast.makeText(currentActivity.getBaseContext(), "Wrong password!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //Showing error message
                    Toast.makeText(currentActivity.getBaseContext(), "Wrong login!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

}