package com.example.app.repository;

import android.util.Log;

import com.example.app.Config;
import com.example.app.OkHttpUtil;
import com.example.app.model.JobPosting;
import com.example.app.restful._WebService;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _RemoteDataSource {
    private _WebService webService;
    private static final int NUM_ITEMS_IN_PAGE = 10;

    private static class Holder {
        private static _RemoteDataSource INSTANCE = new _RemoteDataSource();
    }

    public static _RemoteDataSource getInstance() {
        return _RemoteDataSource.Holder.INSTANCE;
    }

    private _RemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jobs.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpUtil.get())
                .build();
        webService = retrofit.create(_WebService.class);
    }

    public Flowable<List<JobPosting>> getList(String key, int offset) {
        Observable<List<JobPosting>> observable = Observable.create(emitter -> {
            Call<List<JobPosting>> call = webService.getList(key);
            call.enqueue(new Callback<List<JobPosting>>() {
                @Override
                public void onResponse(Call<List<JobPosting>> call, Response<List<JobPosting>> response) {
                    List<JobPosting> listSrc = response.body();
                    //Log.d(Config.TAG, "onResponse - emitter - listSrc size: " + listSrc.size());

                    if (!listSrc.isEmpty()) {
                        JobPosting posting = listSrc.get(0);
                        String companyLogo = posting.getCompanyLogo();
                        //Log.d(Config.TAG, "companyLogo: " + companyLogo);
                    }

                    int size = Math.min(listSrc.size(), (offset + NUM_ITEMS_IN_PAGE));
                    Log.d(Config.TAG, "start: " + offset + ", end: " + size);
                    emitter.onNext(listSrc.subList(0, size));
                    emitter.onComplete();
                }

                @Override
                public void onFailure(Call<List<JobPosting>> call, Throwable t) {
                    Log.d(Config.TAG, "onFailure - emitter: " + t.getMessage());
                    emitter.onError(t);
                }
            });
        });

        Flowable<List<JobPosting>> flowable = observable.toFlowable(BackpressureStrategy.BUFFER);
        return flowable;
    }
}
