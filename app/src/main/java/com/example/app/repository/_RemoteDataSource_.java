package com.example.app.repository;

import android.util.Log;

import com.example.app.model.JobPosting;
import com.example.app.restful.OkHttpUtil;
import com.example.app.restful._WebService;
import com.example.app.util.Config;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _RemoteDataSource_ {
    public static final String BASE_URL = "https://jobs.github.com";
    private _WebService webService;
    private static final int NUM_ITEMS_IN_PAGE = 10;


    private static class Holder {
        private static _RemoteDataSource_ INSTANCE = new _RemoteDataSource_();
    }

    public static _RemoteDataSource_ getInstance() {
        return _RemoteDataSource_.Holder.INSTANCE;
    }

    private _RemoteDataSource_() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpUtil.INSTANCE.getClient())
                .build();
        webService = retrofit.create(_WebService.class);
    }

    public Flowable<List<JobPosting>> getList(String key, int offset) {
        Observable<List<JobPosting>> observable = Observable.create(emitter -> {
            Call<List<JobPosting>> call = webService.getList(key);
            if (false) {
                call.enqueue(new Callback<List<JobPosting>>() {
                    @Override
                    public void onResponse(Call<List<JobPosting>> call, Response<List<JobPosting>> response) {
                        List<JobPosting> listSrc = response.body();
                        //Log.d(Config.TAG, "onResponse - emitter - listSrc size: " + listSrc.size());
                        getListInternal(listSrc, offset, emitter);
                    }

                    @Override
                    public void onFailure(Call<List<JobPosting>> call, Throwable t) {
                        Log.d(Config.TAG, "onFailure - emitter: " + t.getMessage());
                        emitter.onError(t);
                    }
                });
            } else {
                try {
                    Response<List<JobPosting>> execute = call.execute();
                    List<JobPosting> list = execute.body();
                    getListInternal(list, offset, emitter);
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        });

        Flowable<List<JobPosting>> flowable = observable.toFlowable(BackpressureStrategy.BUFFER);
        return flowable;
    }

    private void getListInternal(List<JobPosting> listSrc, int offset, ObservableEmitter<List<JobPosting>> emitter) {
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

    public Maybe<JobPosting> getId(String id) {
        Maybe<JobPosting> maybe = Maybe.create(new MaybeOnSubscribe<JobPosting>() {
            @Override
            public void subscribe(MaybeEmitter<JobPosting> emitter) throws Exception {
                Call<JobPosting> call = webService.getId(id);
                call.enqueue(new Callback<JobPosting>() {
                    @Override
                    public void onResponse(Call<JobPosting> call, Response<JobPosting> response) {
                        emitter.onSuccess(response.body());
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<JobPosting> call, Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        });
        return maybe;
    }


}
