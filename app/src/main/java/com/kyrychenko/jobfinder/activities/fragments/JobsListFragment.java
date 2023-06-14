package com.kyrychenko.jobfinder.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kyrychenko.jobfinder.MainViewModel;
import com.kyrychenko.jobfinder.R;
import com.kyrychenko.jobfinder.activities.MainActivity;
import com.kyrychenko.jobfinder.database.entities.Job;
import com.kyrychenko.jobfinder.recyclerViewHandlers.RVjobsAdapter;

import java.util.List;

public class JobsListFragment extends Fragment {
    private MainViewModel viewModel;

    private RecyclerView recyclerView;
    private RVjobsAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;

    private FloatingActionButton addFab;

    private boolean isAdmin;

    //Just for deleting
    private List<Job> jobsList;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        //Getting user role from activity
        isAdmin = getActivity().getIntent().getExtras().getBoolean("IS_ADMIN");

        View view = inflater.inflate(R.layout.fragment_jobs_list, container, false);

        addFab = (FloatingActionButton) view.findViewById(R.id.adding_fab);

        //Initializing RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.jobs_recycler);
        recyclerView.setHasFixedSize(true);

        //Initializing and setting RecyclerView layout
        recyclerLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(recyclerLayoutManager);

        //Initializing RecyclerView adapter
        recyclerAdapter = new RVjobsAdapter(getContext(), isAdmin);
        //Setting on click listener to delete button in the recycler
        recyclerAdapter.setOnDeleteButtonClickListener(new RVjobsAdapter.OnDeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClick(int position) {
                //Deleting job from recycler using position and general list of jobs
                viewModel.deleteJob(jobsList.get(position));
            }
        });
        recyclerView.setAdapter(recyclerAdapter);

        //Obtaining ViewModel
        viewModel = MainActivity.obtainViewModel(getActivity());
        viewModel.getAllJobs().observe(getViewLifecycleOwner(), new Observer<List<Job>>() {
            @Override
            public void onChanged(List<Job> jobs) {
                //On change data in the database
                // -> resetting adapter jobs list to new
                recyclerAdapter.setJobs(jobs);
                //Notifying adapter that data was changed
                recyclerAdapter.notifyDataSetChanged();

                //Updating general fragment jobs list
                jobsList = jobs;
            }
        });

        if(isAdmin){
            //If user role is admin
            //Showing and enabling Add button
            addFab.setEnabled(true);
            addFab.setVisibility(View.VISIBLE);

            //Getting current activity
            final FragmentActivity currentActivity = getActivity();

            addFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //On Add button click
                    // -> showing another fragment and adding current to backstack
                    currentActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragments_container, new JobsAddingFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        return view;
    }
}