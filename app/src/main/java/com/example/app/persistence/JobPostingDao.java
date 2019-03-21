package com.example.app.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.app.model.JobPosting;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/*
https://developer.android.com/training/data-storage/room/accessing-data#java
https://developer.android.com/topic/libraries/architecture/room
https://developer.android.com/training/data-storage/room/defining-data
*/
@Dao
public interface JobPostingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(JobPosting job);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(JobPosting... jobs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<JobPosting> jobs);

    @Query("DELETE FROM job_table")
    void deleteAll();

    @Query("SELECT * from job_table LIMIT 1")
    Maybe<JobPosting> loadOne();

    @Query("SELECT * from job_table where id = :id")
    Maybe<JobPosting> loadOne(String id);

    @Query("SELECT * from job_table ORDER BY id ASC")
    Flowable<List<JobPosting>> loadList();
}