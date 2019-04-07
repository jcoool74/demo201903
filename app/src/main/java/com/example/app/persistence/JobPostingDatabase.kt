package com.example.app.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import com.example.app.model.JobPosting

/*
https://github.com/googlesamples/android-architecture-components/tree/master/BasicRxJavaSample
https://github.com/googlesamples/android-architecture-components/blob/master/BasicRxJavaSampleKotlin/app/src/main/java/com/example/android/observability/persistence/UsersDatabase.kt
 */

/**
 * The Room database that contains the Users table
 */
@Database(entities = [JobPosting::class], version = 1)
abstract class JobPostingDatabase : RoomDatabase() {

    abstract fun dao(): _Dao

    companion object {
        @Volatile
        private var INSTANCE: JobPostingDatabase? = null

        fun getInstance(context: Context): JobPostingDatabase {
                return INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }
        }

        private fun buildDatabase(context: Context): JobPostingDatabase {
            return Room.databaseBuilder(context.applicationContext,
                    JobPostingDatabase::class.java, "job_database.db")
                    .build()
        }
    }

}
