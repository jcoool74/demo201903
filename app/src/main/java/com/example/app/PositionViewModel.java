package com.example.app;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PositionViewModel extends ViewModel {
    private MutableLiveData<List<Position>> mutableLiveData;

    public MutableLiveData<List<Position>> getMutableLiveData() {
        return mutableLiveData;
    }

    public LiveData<List<Position>> getPositions() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            mutableLiveData.setValue(new ArrayList<>());
        }
        return mutableLiveData;
    }

    @SuppressLint("CheckResult")
    public LiveData<List<Position>> getPositions(int offset) {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            mutableLiveData.setValue(new ArrayList<>());
        }

        GithubJobRepository.getPositions("Java", offset).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(positions -> {
            Log.d(Config.TAG, "subscribe: " + positions.size());
            mutableLiveData.postValue(positions);
        }, err -> {
            // do something
        });

        return mutableLiveData;
    }
}
