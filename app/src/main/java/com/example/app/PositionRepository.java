package com.example.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PositionRepository {
    public static final String TAG = PositionRepository.class.getSimpleName();

    public static LiveData<List<Position>> getPositions(String key) {
        MutableLiveData<List<Position>> data = new MutableLiveData();

        Retrofit retrofit = APIClient.getClient();
        GithubJobWebService webService = retrofit.create(GithubJobWebService.class);
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

    public static LiveData<List<Position>> getPositions_(String key) {
        MutableLiveData<List<Position>> data = new MutableLiveData();

        Retrofit retrofit = APIClient.getClient();
        GithubJobWebService webService = retrofit.create(GithubJobWebService.class);
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
