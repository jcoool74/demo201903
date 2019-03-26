package com.example.app;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.app.model.JobPosting;
import com.example.app.persistence._Database;
import com.example.app.repository._RemoteDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertEquals;

/*
https://github.com/googlesamples/android-architecture
https://github.com/googlesamples/android-architecture/blob/dev-todo-mvvm-rxjava/todoapp/app/src/androidTest/java/com/example/android/architecture/blueprints/todoapp/data/TasksLocalDataSourceTest.java */

@RunWith(AndroidJUnit4.class)
public class RemoteSourceTest {
    private static final String TAG = RemoteSourceTest.class.getSimpleName();

    @Before
    public void setup() {
    }

    @After
    public void cleanUp() {
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.app", appContext.getPackageName());
    }

    @Test
    public void getList() {
        Flowable<List<JobPosting>> flowable = _RemoteDataSource.getInstance().getList("java", 0);
        TestSubscriber<List<JobPosting>> testSubscriber = new TestSubscriber<>();
        flowable.subscribe(testSubscriber);

        List<List<JobPosting>> values = testSubscriber.values();
        Log.d(TAG, "getList - values: " + values.size());

        for (int i = 0; i < values.size(); i++) {
            List<JobPosting> jobPostings = values.get(i);
            for (int j = 0; j < jobPostings.size(); j++) {
                Log.d(TAG, "getList: " + jobPostings.get(j).getTitle());
            }
        }

        testSubscriber.assertNoErrors();
        testSubscriber.assertComplete();

    }


}
