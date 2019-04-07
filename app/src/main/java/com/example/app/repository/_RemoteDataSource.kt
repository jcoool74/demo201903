package com.example.app.repository

import android.util.Log

import com.example.app.util.Config
import com.example.app.restful.OkHttpUtil
import com.example.app.model.JobPosting
import com.example.app.restful._WebService

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter
import io.reactivex.MaybeOnSubscribe
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class _RemoteDataSource  {
    private val BASE_URL = "https://jobs.github.com"
    private val NUM_ITEMS_IN_PAGE = 10
    private val webService: _WebService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpUtil.client)
                .build()
        webService = retrofit.create(_WebService::class.java)
    }

    fun getList(key: String, offset: Int): Flowable<List<JobPosting>> {
        val observable = Observable.create<List<JobPosting>> { emitter ->
            val call = webService.getList(key)
            if (false) {
                call.enqueue(object : Callback<List<JobPosting>> {
                    override fun onResponse(call: Call<List<JobPosting>>, response: Response<List<JobPosting>>) {
                        val listSrc = response.body()
                        //Log.d(Config.TAG, "onResponse - emitter - listSrc size: " + listSrc.size());
                        getListInternal(listSrc!!, offset, emitter)
                    }

                    override fun onFailure(call: Call<List<JobPosting>>, t: Throwable) {
                        Log.d(Config.TAG, "onFailure - emitter: " + t.message)
                        emitter.onError(t)
                    }
                })
            } else {
                try {
                    val execute = call.execute()
                    val list = execute.body()
                    getListInternal(list!!, offset, emitter)
                } catch (e: Exception) {
                    e.printStackTrace()
                    emitter.onError(e)
                }

            }
        }

        return observable.toFlowable(BackpressureStrategy.BUFFER)
    }

    private fun getListInternal(listSrc: List<JobPosting>, offset: Int, emitter: ObservableEmitter<List<JobPosting>>) {
        if (!listSrc.isEmpty()) {
            val posting = listSrc[0]
            val companyLogo = posting.companyLogo
            //Log.d(Config.TAG, "companyLogo: " + companyLogo);
        }

        val size = Math.min(listSrc.size, offset + NUM_ITEMS_IN_PAGE)
        Log.d(Config.TAG, "start: $offset, end: $size")
        emitter.onNext(listSrc.subList(0, size))
        emitter.onComplete()
    }

    fun getId(id: String): Maybe<JobPosting> {
        return Maybe.create { emitter ->
            val call = webService.getId(id)
            call.enqueue(object : Callback<JobPosting> {
                override fun onResponse(call: Call<JobPosting>, response: Response<JobPosting>) {
                    val body = response.body()
                    body ?: emitter.onError(Throwable("body is null"))
                    emitter.onSuccess(body!!)
                    emitter.onComplete()
                }

                override fun onFailure(call: Call<JobPosting>, t: Throwable) {
                    emitter.onError(t)
                }
            })
        }
    }
}
