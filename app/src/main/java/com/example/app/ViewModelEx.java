package com.example.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewModelEx extends ViewModel {
    private MutableLiveData<List<Position>> list;

    public LiveData<List<Position>> getList() {
        if (list == null) {
            list = new MutableLiveData<>();
            list.setValue(new ArrayList<>());
        }
        return list;
    }

    public void addList(List<Position> _list) {
        if (list == null) {
            list = new MutableLiveData<>();
            list.setValue(new ArrayList<>());
        }
        this.list.getValue().clear();
        this.list.getValue().addAll(_list);
    }
}
