package com.kyrychenko.jobfinder.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.kyrychenko.jobfinder.MainViewModel;
import com.kyrychenko.jobfinder.R;
import com.kyrychenko.jobfinder.activities.MainActivity;
import com.kyrychenko.jobfinder.database.entities.Job;


public class JobsAddingFragment extends Fragment {
    private EditText jobTitleEditText, jobDescriptionEditText, jobSalaryEditText;
    private MaterialButton confirmButton;

    private MainViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs_adding, container, false);

        jobTitleEditText = (EditText) view.findViewById(R.id.job_title);
        jobDescriptionEditText = (EditText) view.findViewById(R.id.job_description);
        jobSalaryEditText = (EditText) view.findViewById(R.id.job_salary);

        confirmButton = (MaterialButton) view.findViewById(R.id.confirm_button);

        viewModel = MainActivity.obtainViewModel(getActivity());

        //Joing all fields to
        final EditText[] fields = {jobTitleEditText, jobDescriptionEditText, jobSalaryEditText};
        //Saving current activity
        final FragmentActivity currentActivity = getActivity();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fieldsAreEmpty(fields)){
                    Toast.makeText(currentActivity.getBaseContext(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                    //Exit from method
                    return;
                }

                //Getting values
                String title = jobTitleEditText.getText().toString();
                String description = jobDescriptionEditText.getText().toString();
                Integer salary = Integer.parseInt(jobSalaryEditText.getText().toString());

                //Creating new object
                Job job = new Job(null, title, description, salary);
                //Inserting object to the database
                viewModel.insertJob(job);

                //Returning to the previous fragment
                currentActivity.getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    //Method to check if any of field is empty (characters < 2)
    private boolean fieldsAreEmpty(EditText[] fields){
        for (EditText field : fields) {
            if(field.getText().length() < 2)
                return true;
        }
        return false;
    }
}