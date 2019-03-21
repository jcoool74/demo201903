package com.example.app.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.app.util.Config;
import com.example.app.model.JobPosting;
import com.example.app.repository._Repository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
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
    private MutableLiveData<JobPosting> one = new MutableLiveData<>();

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ViewModelEx(_Repository repository) {
        this.repository = repository;
        List<JobPosting> value = list.getValue();
        if (value == null || value.isEmpty()) {
            loadOrFetchList(0);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
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

    public String getName() {
        String result = "getName";
        List<JobPosting> value = list.getValue();
        if (value != null && !value.isEmpty()) {
            JobPosting jobPosting = value.get(0);
            result = jobPosting.getCompany();
        }
        Log.d(Config.TAG, "getName: " + result);
        return result;
    }

    public String getCompany() {
        String result = "getCompany";
        List<JobPosting> value = list.getValue();
        if (value != null && !value.isEmpty()) {
            JobPosting jobPosting = value.get(0);
            result = jobPosting.getCompany();
        }
        Log.d(Config.TAG, "getCompany: " + result);
        return result;
    }

    public String getDescription() {
        String result = "getDescription";
        List<JobPosting> value = list.getValue();
        if (value != null && !value.isEmpty()) {
            JobPosting jobPosting = value.get(0);
            result = jobPosting.getDescription();
        }
        Log.d(Config.TAG, "getDescription: " + result);
        return result;
    }

    public String getCompanyLogo() {
        //companyLogo
        String result = "https://cdn-images-1.medium.com/max/800/1*pqS__vR2bkJaPAh4OHP7OQ.png";
        List<JobPosting> value = list.getValue();
        if (value != null && !value.isEmpty()) {
            JobPosting jobPosting = value.get(0);
            result = jobPosting.getCompanyLogo();
        }
        Log.d(Config.TAG, "getDescription: " + result);
        return result;
    }

    public LiveData<List<JobPosting>> getList() {
        return list;
    }

    public LiveData<List<JobPosting>> loadMoreList(int offset) {
        loadOrFetchList(offset);
        return list;
    }

    private Flowable<List<JobPosting>> loadOrFetchList(int offset) {
        Log.d(Config.TAG, "loadOrFetchList");

        Flowable<List<JobPosting>> flowable = repository.getList("java", offset);
        Disposable disposable = flowable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(result -> {
            Log.d(Config.TAG, "loadOrFetchList - next: " + result.size());
            list.postValue(result);
            //notifyChange();
            //notifyPropertyChanged(BR);
        }, error -> {
            Log.d(Config.TAG, "loadOrFetchList - err: " + error.getMessage());
        }, () -> {
            Log.d(Config.TAG, "loadOrFetchList - complete");
        });

        compositeDisposable.add(disposable);

        return flowable;
    }


    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) return;
        Glide.with(imageView.getContext()).load(url).apply(RequestOptions.circleCropTransform()).into(imageView);
    }

    @SuppressLint("CheckResult")
    public LiveData<JobPosting> getOne(String id) {
        repository.getOne(id).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(result -> {
            Log.d(Config.TAG, "getOne : " + result);
            one.postValue(result);
        }, err -> {
            Log.d(Config.TAG, "getOne - err: " + err.getMessage());
        }, () -> {
            Log.d(Config.TAG, "getOne - complete");
        });
        return one;
    }
}
