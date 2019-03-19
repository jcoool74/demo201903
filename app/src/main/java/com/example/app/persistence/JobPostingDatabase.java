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
public abstract class JobPostingDatabase extends RoomDatabase {

    private static volatile JobPostingDatabase INSTANCE;

    public abstract JobPostingDao userDao();

    public static JobPostingDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (JobPostingDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            JobPostingDatabase.class, "job_posting_database.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
