package com.example.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class GithubJobRepository {
    public static final String TAG = GithubJobRepository.class.getSimpleName();

    public static io.reactivex.Observable<List<Position>> getPositions(String key, int offset) {
        GithubJobRemoteDataSource remoteDataSource = GithubJobRemoteDataSource.getInstance();
        io.reactivex.Observable<List<Position>> positionListObservable = remoteDataSource.getPositionListObservable(key, offset);
        return positionListObservable;
    }

}
