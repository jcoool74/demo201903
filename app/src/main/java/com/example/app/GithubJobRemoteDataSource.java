package com.example.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

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

    public Observable<List<Position>> getPositionListObservable(String key) {
        Observable<List<Position>> observable = Observable.create(new ObservableOnSubscribe<List<Position>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Position>> emitter) throws Exception {
                Call<List<Position>> call = webService.getPositions(key);
                call.enqueue(new Callback<List<Position>>() {
                    @Override
                    public void onResponse(Call<List<Position>> call, Response<List<Position>> response) {
                        List<Position> body = response.body();
                        Log.d(Config.TAG, "emitter-res: " + body.size());

                        if (!body.isEmpty()) {
                            Position position = body.get(0);
                            String companyLogo = position.getCompanyLogo();
                            Log.d(Config.TAG, "companyLogo: " + companyLogo);
                        }

                        body.add(null);

                        emitter.onNext(body);
                    }

                    @Override
                    public void onFailure(Call<List<Position>> call, Throwable t) {
                        Log.d(Config.TAG, "emitter-err: " + t.getMessage());
                        emitter.onError(t);
                    }
                });
            }
        });
        return observable;
    }

    public LiveData<List<Position>> getPositionListLiveData(String key) {
        MutableLiveData<List<Position>> data = new MutableLiveData();

        Call<List<Position>> call = webService.getPositions(key);
        call.enqueue(new Callback<List<Position>>() {
            @Override
            public void onResponse(Call<List<Position>> call, Response<List<Position>> response) {
                List<Position> _list = response.body();
                Log.d(Config.TAG, "onResponse - _list: " + _list.size());

                for (Position position : _list) {
                    Log.d(Config.TAG, "onResponse - position: " + position.getTitle());
                }

                _list.add(null);
                Log.d(Config.TAG, "onResponse - _list: " + _list.size());

                data.setValue(_list);
            }

            @Override
            public void onFailure(Call<List<Position>> call, Throwable t) {
                Log.d(Config.TAG, "onFailure");
            }
        });

        return data;
    }


}
