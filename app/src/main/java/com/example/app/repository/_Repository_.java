package com.example.app.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.app.model.JobPosting;
import com.example.app.persistence._Dao;
import com.example.app.util.Config;
import com.example.app.util.RateLimiter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

/*
_Repository Pattern
https://developer.android.com/jetpack/docs/guide

RateLimiter
https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/util/RateLimiter.kt
https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/RepoRepository.kt
 */
public class _Repository_ {
//    @Inject
    _RemoteDataSource remoteDataSource;
//    @Inject
    _Dao dao;

    private final RateLimiter<String> rateLimiter = new RateLimiter<>(10, TimeUnit.MINUTES);

    private static final Object KEY_GET_ONE = "KEY_GET_ONE";
    private final static String KEY_GET_LIST = "KEY_GET_LIST";

    public _Repository_(_RemoteDataSource remoteDataSource, _Dao dao) {
        this.remoteDataSource = remoteDataSource;
        this.dao = dao;
    }

    @SuppressLint("CheckResult")
    public Maybe<JobPosting> getOne(final String id) {
        String key = KEY_GET_ONE + id;

        dao.loadOne(id).observeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(result -> {
            final boolean shouldFetch = (result == null || rateLimiter.shouldFetch(key));
            Log.d(Config.TAG, "getOne - shouldFetch: " + shouldFetch + ", result: " + result);

            if (shouldFetch) {
                fetchOne(id);
            }
        }, err -> {
            rateLimiter.reset(key);
        }, () -> {
        });

        return dao.loadOne(id);
    }

    @SuppressLint("CheckResult")
    public Flowable<List<JobPosting>> getList(String keyword, int offset) {
        String key = KEY_GET_LIST + offset;

        dao.loadList().subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(result -> {
            final boolean shouldFetch = (result == null || result.isEmpty() || rateLimiter.shouldFetch(key));
            Log.d(Config.TAG, "getList - shouldFetch: " + shouldFetch + ", result: " + result);
            Log.d(Config.TAG_SPECIAL, "2222-try-to-fetch");

            if (shouldFetch) {
                // this is an action for next time use
                fetchAndSaveList(keyword, offset);
            }
        }, error -> {
            rateLimiter.reset(key);
        }, () -> {
        });


        Log.d(Config.TAG_SPECIAL, "0000-started");

        return dao.loadList();
    }

    @SuppressLint("CheckResult")
    private void fetchOne(String id) {
        String key = KEY_GET_ONE + id;

        remoteDataSource.getId(id).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(result -> {
            final boolean shouldFetch = (result == null || rateLimiter.shouldFetch(key));
            if (shouldFetch) {
                // TODO: fetch and save individual item
            }
        }, err -> {
        }, () -> {
        });
    }

    @SuppressLint("CheckResult")
    private void fetchAndSaveList(String key, int offset) {
        remoteDataSource.getList(key, offset).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(result -> {
            try {
                Log.d(Config.TAG, "fetchAndSaveList - from remoteDataSource: " + result.size());
                saveList(result);
                checkSaved();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(Config.TAG, "fetchAndSaveList - e", e);
            }
        }, err -> {
            Log.d(Config.TAG, "fetchAndSaveList - err", err);
        }, () -> {
            Log.d(Config.TAG, "fetchAndSaveList - complete");
        });
    }

    private void saveList(List<JobPosting> result) {
        dao.insert(result);
        Log.d(Config.TAG_SPECIAL, "3333-save");
    }

    @SuppressLint("CheckResult")
    private void checkSaved() {
        // This is just for logging
        Flowable<List<JobPosting>> flowable = dao.loadList();
        flowable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(result -> {
            Log.d(Config.TAG, "checkSaved - result: " + result.size());
        }, err -> {
            Log.d(Config.TAG, "checkSaved - err: " + err);
        }, () -> {
            Log.d(Config.TAG, "checkSaved - complete");
        });
    }


}
