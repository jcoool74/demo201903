package com.example.app

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log

import com.example.app.model.JobPosting
import com.example.app.repository._RemoteDataSource

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import io.reactivex.subscribers.TestSubscriber

import org.junit.Assert.assertEquals

/*
https://github.com/googlesamples/android-architecture
https://github.com/googlesamples/android-architecture/blob/dev-todo-mvvm-rxjava/todoapp/app/src/androidTest/java/com/example/android/architecture/blueprints/todoapp/data/TasksLocalDataSourceTest.java */

@RunWith(AndroidJUnit4::class)
class RemoteSourceTest {

    @Before
    fun setup() {
    }

    @After
    fun cleanUp() {
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.app", appContext.packageName)
    }

    @Test
    fun getList() {
        val flowable = _RemoteDataSource.getList("java", 0)
        val testSubscriber = TestSubscriber<List<JobPosting>>()
        flowable.subscribe(testSubscriber)

        val values = testSubscriber.values()
        Log.d(TAG, "getList - values: " + values.size)

        for (i in values.indices) {
            val jobPostings = values[i]
            for (j in jobPostings.indices) {
                Log.d(TAG, "getList: " + jobPostings[j].title!!)
            }
        }

        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

    }

    companion object {
        private val TAG = RemoteSourceTest::class.java.simpleName
    }


}
