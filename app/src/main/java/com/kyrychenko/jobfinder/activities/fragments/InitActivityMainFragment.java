package com.kyrychenko.jobfinder.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.kyrychenko.jobfinder.R;

public class InitActivityMainFragment extends Fragment implements View.OnClickListener {
    private MaterialButton loginButton, registerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init_activity_main, container, false);

        loginButton = (MaterialButton) view.findViewById(R.id.login_button);
        registerButton = (MaterialButton) view.findViewById(R.id.register_button);

        //We implemented interface to shorten the code here
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        Fragment selected = null;

        //Checking which button was pressed
        if(view.getId() == R.id.login_button){
            selected = new InitActivityLoginFragment();
        }else {
            selected = new InitActivityRegisterFragment();
        }

        //Replacing container with fragment which was selected
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.init_container, selected)
                .addToBackStack(null)
                .commit();
    }
}