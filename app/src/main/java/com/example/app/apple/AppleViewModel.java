package com.example.app.apple;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.app.Position;

//https://developer.android.com/topic/libraries/data-binding/architecture
//https://developer.android.com/topic/libraries/architecture/viewmodel
public class AppleViewModel extends ViewModel {
    private MutableLiveData<Position> positionLiveData = new MutableLiveData<>();

    public AppleViewModel() {
        Position value = new Position();
        value.setTitle("lkjlskdjfkslfdsdkfs");
        positionLiveData.setValue(value);
    }

    public LiveData<Position> getPosition() {
        return positionLiveData;
    }

    public String getTitle() {
        return positionLiveData.getValue().getTitle();
    }
}
