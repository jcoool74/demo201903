package com.example.app.repository

import android.annotation.SuppressLint
import android.util.Log

import com.example.app.util.Config
import com.example.app.model.JobPosting
import com.example.app.persistence._Dao
import com.example.app.util.RateLimiter
import java.util.concurrent.TimeUnit

import javax.inject.Inject

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

/*
_Repository Pattern
https://developer.android.com/jetpack/docs/guide

RateLimiter
https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/util/RateLimiter.kt
https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/RepoRepository.kt
 */
class _Repository(@field:Inject
                  internal var remoteDataSource: _RemoteDataSource, @field:Inject
                  internal var dao: _Dao) {

    companion object {
        private const val KEY_GET_ONE = "KEY_GET_ONE"
        private const val KEY_GET_LIST = "KEY_GET_LIST"
    }

    private val rateLimiter = RateLimiter<String>(10, TimeUnit.MINUTES)

    @SuppressLint("CheckResult")
    fun getOne(id: String): Maybe<JobPosting> {
        val key = KEY_GET_ONE + id

        dao.loadOne(id).observeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe({ result ->
            val shouldFetch = result == null || rateLimiter.shouldFetch(key)
            Log.d(Config.TAG, "getOne - shouldFetch: $shouldFetch, result: $result")

            if (shouldFetch) {
                fetchOne(id)
            }
        }, { err -> rateLimiter.reset(key) }, { })

        return dao.loadOne(id)
    }

    @SuppressLint("CheckResult")
    fun getList(keyword: String, offset: Int): Flowable<List<JobPosting>> {
        val key = KEY_GET_LIST + offset

        dao.loadList().subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe({ result ->
            val shouldFetch = result == null || result.isEmpty() || rateLimiter.shouldFetch(key)
            Log.d(Config.TAG, "getList - shouldFetch: $shouldFetch, result: $result")
            Log.d(Config.TAG_SPECIAL, "2222-try-to-fetch")

            if (shouldFetch) {
                // this is an action for next time use
                fetchAndSaveList(keyword, offset)
            }
        }, { error -> rateLimiter.reset(key) }, { })


        Log.d(Config.TAG_SPECIAL, "0000-started")

        return dao.loadList()
    }

    @SuppressLint("CheckResult")
    private fun fetchOne(id: String) {
        val key = KEY_GET_ONE + id

        remoteDataSource.getId(id).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe({ result ->
            val shouldFetch = result == null || rateLimiter.shouldFetch(key)
            if (shouldFetch) {
                // TODO: fetch and save individual item
            }
        }, { err -> }, { })
    }

    @SuppressLint("CheckResult")
    private fun fetchAndSaveList(key: String, offset: Int) {
        remoteDataSource.getList(key, offset).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe({ result ->
            try {
                Log.d(Config.TAG, "fetchAndSaveList - from remoteDataSource: " + result.size)
                saveList(result)
                checkSaved()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(Config.TAG, "fetchAndSaveList - e", e)
            }
        }, { err -> Log.d(Config.TAG, "fetchAndSaveList - err", err) }, { Log.d(Config.TAG, "fetchAndSaveList - complete") })
    }

    private fun saveList(result: List<JobPosting>) {
        dao.insert(result)
        Log.d(Config.TAG_SPECIAL, "3333-save")
    }

    @SuppressLint("CheckResult")
    private fun checkSaved() {
        // This is just for logging
        val flowable = dao.loadList()
        flowable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe({ result -> Log.d(Config.TAG, "checkSaved - result: " + result.size) }, { err -> Log.d(Config.TAG, "checkSaved - err: $err") }, { Log.d(Config.TAG, "checkSaved - complete") })
    }



}
