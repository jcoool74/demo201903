package com.example.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubJobRemoteDataSource {
    private static final String TAG = GithubJobRemoteDataSource.class.getSimpleName();
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

    public LiveData<List<Position>> getPositions(String key) {
        MutableLiveData<List<Position>> data = new MutableLiveData();

        Call<List<Position>> call = webService.getPositions(key);
        call.enqueue(new Callback<List<Position>>() {
            @Override
            public void onResponse(Call<List<Position>> call, Response<List<Position>> response) {
                List<Position> _list = response.body();
                for (Position position : _list) {
                    Log.d(TAG, "onResponse - position: " + position.getTitle());
                }
                data.setValue(_list);
            }

            @Override
            public void onFailure(Call<List<Position>> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });

        return data;
    }


}
