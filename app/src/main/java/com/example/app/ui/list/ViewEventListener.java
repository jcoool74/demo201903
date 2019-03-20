package com.example.app.ui.list;

import android.util.Log;

import com.example.app.model.JobPosting;

public class ViewEventListener {
    private static final String TAG = ViewEventListener.class.getSimpleName();
    private InteractionListener interactionListener;

    public ViewEventListener(InteractionListener listener) {
        this.interactionListener = listener;
    }

    public void onClick(JobPosting job) {
        Log.d(TAG, "onClick: " + job.getCompany());
        interactionListener.onListItemClick(job.getId());
    }
}
