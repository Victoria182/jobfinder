package com.kyrychenko.jobfinder.recyclerViewHandlers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.kyrychenko.jobfinder.R;
import com.kyrychenko.jobfinder.database.entities.Job;

import java.util.ArrayList;
import java.util.List;

//Classic implementation of RecyclerView
public class RVjobsAdapter extends RecyclerView.Adapter<RVjobsAdapter.JobViewHolder>{
    private final LayoutInflater inflater;
    private List<Job> jobs = new ArrayList<>();
    private final Context context;
    private final boolean isAdmin;

    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public RVjobsAdapter(Context context, boolean isAdmin) {
        this.context = context;
        this.isAdmin = isAdmin;
        inflater = LayoutInflater.from(this.context);
    }

    //Interface to set onClickListener on the each card or its inner elements
    public interface OnDeleteButtonClickListener{
        void onDeleteButtonClick(int position);
    }

    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener){
        this.onDeleteButtonClickListener = listener;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.job_card, parent, false);
        return new JobViewHolder(view, onDeleteButtonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        if(jobs != null){
            //Setting values for card
            Job current = jobs.get(position);
            holder.getJobTitleTextView().setText(current.getTitle());
            holder.getJobDescriptionTextView().setText(current.getDescription());
            holder.getJobSalaryTextView().setText(String.valueOf(current.getSalary()) + '$');

            if(isAdmin){
                MaterialButton deleteButton = holder.getDeleteButton();
                deleteButton.setEnabled(true);
                deleteButton.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setJobs(List<Job> jobs){
        this.jobs = jobs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(jobs == null)
            return 0;
        return jobs.size();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder{
        private final TextView jobTitleTextView;
        private final TextView jobDescriptionTextView;
        private final TextView jobSalaryTextView;

        private final MaterialButton deleteButton;

        public JobViewHolder(@NonNull View itemView, OnDeleteButtonClickListener listener) {
            super(itemView);

            jobTitleTextView = (TextView) itemView.findViewById(R.id.job_title);
            jobDescriptionTextView = (TextView) itemView.findViewById(R.id.job_description);
            jobSalaryTextView = (TextView) itemView.findViewById(R.id.job_salary);

            deleteButton = (MaterialButton) itemView.findViewById(R.id.delete_button);
            //Setting our external onClickListener in the default inner
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onDeleteButtonClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            onDeleteButtonClickListener.onDeleteButtonClick(position);
                            notifyItemRemoved(position);
                        }
                    }
                }
            });
        }

        public TextView getJobTitleTextView() {
            return jobTitleTextView;
        }

        public TextView getJobDescriptionTextView() {
            return jobDescriptionTextView;
        }

        public TextView getJobSalaryTextView() {
            return jobSalaryTextView;
        }

        public MaterialButton getDeleteButton() {
            return deleteButton;
        }
    }
}
