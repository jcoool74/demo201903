package com.example.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;


public class GithubJobRepository {
    public static final String TAG = GithubJobRepository.class.getSimpleName();

    public static LiveData<List<Position>> getPositions(String key) {
        MutableLiveData<List<Position>> mutableLiveData = new MutableLiveData();

        LiveData<List<Position>> liveData = GithubJobRemoteDataSource.getInstance().getPositions(key);
        mutableLiveData.setValue(liveData.getValue());

        return mutableLiveData;
    }

}
