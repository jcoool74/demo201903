package com.example.app.persistence

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.example.app.model.JobPosting

import io.reactivex.Flowable
import io.reactivex.Maybe

/*
https://developer.android.com/training/data-storage/room/accessing-data#java
https://developer.android.com/topic/libraries/architecture/room
https://developer.android.com/training/data-storage/room/defining-data
*/
@Dao
interface _Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(job: JobPosting)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg jobs: JobPosting)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(jobs: List<JobPosting>)

    @Query("DELETE FROM job_table")
    fun deleteAll()

    @Query("SELECT * from job_table LIMIT 1")
    fun loadOne(): Maybe<JobPosting>

    @Query("SELECT * from job_table where id = :id")
    fun loadOne(id: String): Maybe<JobPosting>

    @Query("SELECT * from job_table ORDER BY id ASC")
    fun loadList(): Flowable<List<JobPosting>>
}