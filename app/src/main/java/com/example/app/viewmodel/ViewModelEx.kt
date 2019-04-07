package com.example.app.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
//import com.example.app.BR
import com.example.app.util.Config
import com.example.app.model.JobPosting
import com.example.app.repository._Repository

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/*
Bind layout views to Architecture Components
https://developer.android.com/topic/libraries/data-binding/architecture
 */
class ViewModelEx(private val repository: _Repository) : ViewModel(), Observable {

    private val list = MutableLiveData<List<JobPosting>>()
    private val one = MutableLiveData<JobPosting>()

    private val callbacks = PropertyChangeRegistry()
    private val compositeDisposable = CompositeDisposable()

    val title: String
        @Bindable
        get() {
            var result: String? = "getTitle"
            val value = one.value
            if (value != null) {
                result = value.title
            }
            Log.d(Config.TAG, "getTitle: " + result!!)
            return result
        }

    val company: String
        @Bindable
        get() {
            var result: String? = "getCompany"
            val value = one.value
            if (value != null) {
                result = value.company
            }
            Log.d(Config.TAG, "getCompany: " + result!!)
            return result
        }

    val description: String
        @Bindable
        get() {
            var result: String? = "getDescription"
            val value = one.value
            if (value != null) {
                result = value.description
            }
            Log.d(Config.TAG, "getDescription: " + result!!)
            return result
        }

    val companyLogo: String?
        @Bindable
        get() {
            var result: String? = null
            val value = one.value
            if (value != null) {
                result = value.companyLogo
            }
            Log.d(Config.TAG, "getCompanyLogo: " + result)
            return result
        }

    init {
        val value = list.value
        if (value == null || value.isEmpty()) {
            //            loadOrFetchList(0);
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    internal fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    internal fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    fun getList(): LiveData<List<JobPosting>> {
        return list
    }

    fun loadMoreList(offset: Int): LiveData<List<JobPosting>> {
        loadOrFetchList(offset)
        return list
    }

    private fun loadOrFetchList(offset: Int) {
        Log.d(Config.TAG, "loadOrFetchList")

        val flowable = repository.getList("java", offset)
        val disposable = flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
            Log.d(Config.TAG, "loadOrFetchList - next: " + result.size)
            Log.d(Config.TAG_SPECIAL, "1111-" + System.currentTimeMillis() + "-notify-ui")

            // notify the observer
            list.value = result
            // update the UI as well
            notifyChange()
        }, { error -> Log.d(Config.TAG, "loadOrFetchList - err: " + error.message) }, { Log.d(Config.TAG, "loadOrFetchList - complete") })

        //        Log.d(Config.TAG_SPECIAL, "1111");
        compositeDisposable.add(disposable)
    }

    @SuppressLint("CheckResult")
    fun getOne(id: String): LiveData<JobPosting> {
        repository.getOne(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
            Log.d(Config.TAG, "getOne : $result")
            one.value = result
            notifyChange()
        }, { err -> Log.d(Config.TAG, "getOne - err: " + err.message) }, { Log.d(Config.TAG, "getOne - complete") })
        return one
    }

//    companion object {
//        @JvmStatic
//        @BindingAdapter("bind:imageUrl")
//        fun loadImage(imageView: ImageView, url: String) {
//            if (TextUtils.isEmpty(url)) return
//            Glide.with(imageView.context).load(url).apply(RequestOptions.circleCropTransform()).into(imageView)
//        }
//    }
}
