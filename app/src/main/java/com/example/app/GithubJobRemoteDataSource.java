package com.example.app;

import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubJobRemoteDataSource {
    private GithubJobWebService webService;
    private static final int NUM_ITEMS_IN_PAGE = 10;

    private static class Holder {
        private static GithubJobRemoteDataSource INSTANCE = new GithubJobRemoteDataSource();
    }

    public static GithubJobRemoteDataSource getInstance() {
        return Holder.INSTANCE;
    }

    private GithubJobRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jobs.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpUtil.get())
                .build();
        webService = retrofit.create(GithubJobWebService.class);
    }

    public Observable<List<Position>> getPositionListObservable(String key, int offset) {
        Observable<List<Position>> observable = Observable.create(new ObservableOnSubscribe<List<Position>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Position>> emitter) throws Exception {
                Call<List<Position>> call = webService.getPositions(key);
                call.enqueue(new Callback<List<Position>>() {
                    @Override
                    public void onResponse(Call<List<Position>> call, Response<List<Position>> response) {
                        List<Position> listSrc = response.body();
                        //Log.d(Config.TAG, "onResponse - emitter - listSrc size: " + listSrc.size());

                        if (!listSrc.isEmpty()) {
                            Position position = listSrc.get(0);
                            String companyLogo = position.getCompanyLogo();
                            //Log.d(Config.TAG, "companyLogo: " + companyLogo);
                        }

                        int size = Math.min(listSrc.size(), (offset + NUM_ITEMS_IN_PAGE));
                        Log.d(Config.TAG, "start: " + offset + ", end: " + size);
                        emitter.onNext(listSrc.subList(0, size));
                    }

                    @Override
                    public void onFailure(Call<List<Position>> call, Throwable t) {
                        Log.d(Config.TAG, "onFailure - emitter: " + t.getMessage());
                        emitter.onError(t);
                    }
                });
            }
        });
        return observable;
    }
}
