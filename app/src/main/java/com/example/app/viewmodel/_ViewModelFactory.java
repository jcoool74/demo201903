package com.example.app.viewmodel;

import android.app.Activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.example.app.persistence._Database;
import com.example.app.repository._RemoteDataSource;
import com.example.app.repository._Repository;

public class _ViewModelFactory implements ViewModelProvider.Factory {
    private _Repository repository;

    public _ViewModelFactory(_Repository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewModelEx.class)) {
            return (T) new ViewModelEx(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    public static _ViewModelFactory createFactory(Activity activity) {
        _Database database = _Database.getInstance(activity);

        _Repository repository = new _Repository(_RemoteDataSource.getInstance(), database.jobPostingDao());
        _ViewModelFactory factory = new _ViewModelFactory(repository);

        return factory;
    }
}
