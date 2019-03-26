package com.example.app;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.app.model.JobPosting;
import com.example.app.persistence._Database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/*
https://github.com/googlesamples/android-architecture
https://github.com/googlesamples/android-architecture/blob/dev-todo-mvvm-rxjava/todoapp/app/src/androidTest/java/com/example/android/architecture/blueprints/todoapp/data/TasksLocalDataSourceTest.java */

@RunWith(AndroidJUnit4.class)
public class DaoTest {
    private static final String TAG = DaoTest.class.getSimpleName();
    private _Database database;

    @Before
    public void initDB() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), _Database.class).build();
    }

    @After
    public void closeDB() {
        database.close();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.app", appContext.getPackageName());
    }

    private static class Result {
        boolean val;
    }

    @Test
    public void test() {
        final Result ret = new Result();
        JobPosting job = new JobPosting();
        job.setId("1234");
        database.jobPostingDao().insert(job);

        if (true) {
            TestSubscriber<JobPosting> subscriber = new TestSubscriber<>();

            Maybe<JobPosting> maybe = database.jobPostingDao().loadOne(job.getId());
            maybe.toFlowable().subscribe(subscriber);

            subscriber.assertNoErrors();
            subscriber.assertComplete();
            List<JobPosting> values = subscriber.values();
            for (JobPosting elem : values) {
                Log.d(TAG, "getId: " + elem.getId());
            }

//            List<List<Object>> events = subscriber.getEvents();
//            Log.d(TAG, "events: " + events.size());

//            maybe.subscribe((MaybeObserver<? super JobPosting>) subscriber);
//            assertThat(subscriber.getEvents(), hasI);
        } else {
            Maybe<JobPosting> maybe = database.jobPostingDao().loadOne(job.getId());
            maybe.observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
                ret.val = (job.getId().equals(result.getId()));
                Log.d(TAG, "test - ret: " + ret.val);
            }, error -> {
                Log.e(TAG, "test - ret: " + ret.val, error);
            }, () -> {
                Log.d(TAG, "test - ret =: " + ret.val);
            });

            SystemClock.sleep(3000);
            assertEquals(ret.val, true);
        }
    }
}
