package com.example.app.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.example.app.BR;
import com.example.app.model.JobPosting;
import com.example.app.repository._Repository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
/*
Bind layout views to Architecture Components
https://developer.android.com/topic/libraries/data-binding/architecture
 */
public class ViewModelEx extends ViewModel implements Observable {
    private _Repository repository;
    private MutableLiveData<List<JobPosting>> list = new MutableLiveData<>();
    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ViewModelEx(_Repository repository) {
        this.repository = repository;
        List<JobPosting> value = list.getValue();
        if (value == null || value.isEmpty()) {
            _getList();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public String getName() {
        List<JobPosting> value = list.getValue();
        if (value != null && !value.isEmpty()) {
            JobPosting jobPosting = value.get(0);
            return jobPosting.getCompany();
        }
        return "hello_world_cup";
    }

    public LiveData<List<JobPosting>> getList() {
        return list;
    }

    public Flowable<List<JobPosting>> _getList() {
        Flowable<List<JobPosting>> flowable = repository.getList("java", 0);
        Disposable disposable = flowable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(next -> {
            list.setValue(next);
            notifyPropertyChanged(BR.viewModel);
        }, error -> {
        }, () -> {
        });
        compositeDisposable.add(disposable);
        return flowable;
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }
}
