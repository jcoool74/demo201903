package com.example.app.ui.list

import android.util.Log

import com.example.app.model.JobPosting

class ViewEventListener(private val interactionListener: InteractionListener) {

    fun onClick(job: JobPosting) {
        Log.d(TAG, "onClick: " + job.company!!)
        interactionListener.onListItemClick(job.id)
    }

    companion object {
        private val TAG = ViewEventListener::class.java.simpleName
    }
}
