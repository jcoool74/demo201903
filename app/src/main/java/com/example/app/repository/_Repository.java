package com.example.app.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.app.util.Config;
import com.example.app.model.JobPosting;
import com.example.app.persistence.JobPostingDao;
import com.example.app.util.RateLimiter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/*
_Repository Pattern
https://developer.android.com/jetpack/docs/guide

RateLimiter
https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/util/RateLimiter.kt
https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/RepoRepository.kt
 */
public class _Repository {
    private final _RemoteDataSource remoteDataSource;
    private final JobPostingDao dao;
    private final RateLimiter<String> rateLimiter = new RateLimiter<>(10, TimeUnit.MINUTES);
    private final static String KEY_GET_LIST = "loadList";


    public _Repository(_RemoteDataSource remoteDataSource, JobPostingDao dao) {
        this.remoteDataSource = remoteDataSource;
        this.dao = dao;
    }

    @SuppressLint("CheckResult")
    public Flowable<List<JobPosting>> getList(String keyword, int offset) {
        String key = KEY_GET_LIST + offset;

        dao.loadList().subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(next -> {
            final boolean shouldFetch = (next == null || next.isEmpty() || rateLimiter.shouldFetch(key));
            Log.d(Config.TAG, "loadList - shouldFetch: " + shouldFetch + ", key: " + key);

            if (shouldFetch) {
                fetchList(keyword, offset);
            }
        }, error -> {
            rateLimiter.reset(key);
        }, () -> {
        });

        return dao.loadList();
    }

    @SuppressLint("CheckResult")
    private void fetchList(String key, int offset) {
        remoteDataSource.getList(key, offset).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(result -> {
            try {
                Log.d(Config.TAG, "fetchList - from remoteDataSource: " + result.size());
                saveList(result);
                checkSaved();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(Config.TAG, "fetchList - e", e);
            }
        }, err -> {
            Log.d(Config.TAG, "fetchList - err", err);
        }, () -> {
            Log.d(Config.TAG, "fetchList - complete");
        });
    }

    private void saveList(List<JobPosting> result) {
        dao.insert(result);
    }

    @SuppressLint("CheckResult")
    private void checkSaved() {
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
