package com.example.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PositionViewModel extends ViewModel {
    private MutableLiveData<List<Position>> mutableLiveData;

    public LiveData<List<Position>> getPositions() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            mutableLiveData.setValue(new ArrayList<>());
        }

        if (false) {
            LiveData<List<Position>> list = GithubJobRepository.getPositions("Android");
            mutableLiveData.setValue(list.getValue());
        }

        return mutableLiveData;
    }
}
