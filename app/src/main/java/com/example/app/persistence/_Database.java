package com.example.app.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.app.model.JobPosting;

//https://github.com/googlesamples/android-architecture-components/tree/master/BasicRxJavaSample

/**
 * The Room database that contains the Users table
 */
@Database(entities = {JobPosting.class}, version = 1)
public abstract class _Database extends RoomDatabase {

    private static volatile _Database INSTANCE;

    public abstract JobPostingDao jobPostingDao();

    public static _Database getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (_Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            _Database.class, "job_database.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
