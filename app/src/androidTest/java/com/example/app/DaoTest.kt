package com.example.app

import android.arch.persistence.room.Room
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log

import com.example.app.model.JobPosting
import com.example.app.persistence.JobPostingDatabase

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber

import org.junit.Assert.assertEquals

/*
https://github.com/googlesamples/android-architecture
https://github.com/googlesamples/android-architecture/blob/dev-todo-mvvm-rxjava/todoapp/app/src/androidTest/java/com/example/android/architecture/blueprints/todoapp/data/TasksLocalDataSourceTest.java */

@RunWith(AndroidJUnit4::class)
class DaoTest {
    private var database: JobPostingDatabase? = null

    @Before
    fun initDB() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), JobPostingDatabase::class.java).build()
    }

    @After
    fun closeDB() {
        database!!.close()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.app", appContext.packageName)
    }

    private class Result {
        internal var `val`: Boolean = false
    }

    @Test
    fun test() {
        val ret = Result()
        val job = JobPosting()
        job.id = "1234"
        database!!.dao().insert(job)

        if (true) {
            val subscriber = TestSubscriber<JobPosting>()

            val maybe = database!!.dao().loadOne(job.id)
            maybe.toFlowable().subscribe(subscriber)

            subscriber.assertNoErrors()
            subscriber.assertComplete()
            val values = subscriber.values()
            for (elem in values) {
                Log.d(TAG, "getId: " + elem.id)
            }

            //            List<List<Object>> events = subscriber.getEvents();
            //            Log.d(TAG, "events: " + events.size());

            //            maybe.subscribe((MaybeObserver<? super JobPosting>) subscriber);
            //            assertThat(subscriber.getEvents(), hasI);
        } else {
            val maybe = database!!.dao().loadOne(job.id)
            maybe.observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
                ret.`val` = job.id == result.id
                Log.d(TAG, "test - ret: " + ret.`val`)
            }, { error -> Log.e(TAG, "test - ret: " + ret.`val`, error) }, { Log.d(TAG, "test - ret =: " + ret.`val`) })

            SystemClock.sleep(3000)
            assertEquals(ret.`val`, true)
        }
    }

    companion object {
        private val TAG = DaoTest::class.java.simpleName
    }
}
