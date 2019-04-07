package com.example.app.viewmodel

import android.app.Activity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.app.persistence.JobPostingDatabase
import com.example.app.repository._RemoteDataSource
import com.example.app.repository._Repository

class _ViewModelFactory(private val repository: _Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelEx::class.java)) {
            return ViewModelEx(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {

        fun createFactory(activity: Activity): _ViewModelFactory {
            val database = JobPostingDatabase.getInstance(activity)

            val repository = _Repository(_RemoteDataSource(), database!!.dao())

            return _ViewModelFactory(repository)
        }
    }
}
