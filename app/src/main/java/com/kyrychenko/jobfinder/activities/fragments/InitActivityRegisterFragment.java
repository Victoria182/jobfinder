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

public class InitActivityRegisterFragment extends Fragment {
    private MaterialButton registerButton;
    private MainViewModel viewModel;

    private EditText loginEditText, passwordEditText, passwordRepeatEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init_activity_register, container, false);

        //Obtaining general ViewModel from single activity
        viewModel = MainActivity.obtainViewModel(getActivity());

        registerButton = (MaterialButton) view.findViewById(R.id.register_button);
        loginEditText = (EditText) view.findViewById(R.id.login_field);
        passwordEditText = (EditText) view.findViewById(R.id.password_field);
        passwordRepeatEditText = (EditText) view.findViewById(R.id.password_repeat_field);

        //Saving activity to which this fragment belongs
        // 'final' to use in the listener body
        final Activity currentActivity = getActivity();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting entered login
                final String login = loginEditText.getText().toString();
                if(viewModel.findUserByLogin(login) != null){
                    //If such user already exists -> show error
                    Toast.makeText(currentActivity.getBaseContext(), "User with such login is already registered!", Toast.LENGTH_SHORT).show();
                    //Exit from method
                    return;
                }

                //Getting entered passwords
                final String password = passwordEditText.getText().toString();
                final String passwordRepeat = passwordRepeatEditText.getText().toString();

                //Checking are they equal
                if(!password.equals(passwordRepeat)){
                    Toast.makeText(currentActivity.getBaseContext(), "Passwords aren't equal!", Toast.LENGTH_SHORT).show();
                    //Exit method
                    return;
                }

                //If still didn't quit from method..
                //Creating new user with entered values
                User user = new User(null, false, login, password);
                //Inserting to the database
                viewModel.insertUser(user);

                //Going to the next activity
                Intent intent = new Intent(currentActivity, MainActivity.class);
                // with passing value describing the user role
                intent.putExtra("IS_ADMIN", user.isAdmin());
                startActivity(intent);
                //Finishing current activity to remove it from backstack
                currentActivity.finish();
            }
        });

        return view;
    }
}