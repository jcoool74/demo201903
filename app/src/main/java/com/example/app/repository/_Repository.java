package com.example.app.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.app.util.Config;
import com.example.app.model.JobPosting;
import com.example.app.persistence.JobPostingDao;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/*
_Repository Pattern
https://developer.android.com/jetpack/docs/guide
 */
public class _Repository {
    private final _RemoteDataSource remoteDataSource;
    private final JobPostingDao dao;

    public _Repository(_RemoteDataSource remoteDataSource, JobPostingDao dao) {
        this.remoteDataSource = remoteDataSource;
        this.dao = dao;
    }

    @SuppressLint("CheckResult")
    public Flowable<List<JobPosting>> getList(String key, int offset) {
        remoteDataSource.getList(key, offset).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(result -> {
            try {
                Log.d(Config.TAG, "getList - from remoteDataSource: " + result.size());
                dao.insert(result);
                test();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(Config.TAG, "getList - e", e);
            }
        }, error -> {
            Log.d(Config.TAG, "getList - error", error);
        }, () -> {
            Log.d(Config.TAG, "getList - complete");
        });

        return dao.getList();
    }

    @SuppressLint("CheckResult")
    private void test() {
        Flowable<List<JobPosting>> flowable = dao.getList();
        flowable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(it -> {
            Log.d(Config.TAG, "dao - getList: " + it.size());
        }, err -> {
            Log.d(Config.TAG, "dao - getList: " + err);
        }, () -> {
            Log.d(Config.TAG, "dao - getList: done");
        });
    }


}
