package com.example.app;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repo {
    public static final String TAG = Repo.class.getSimpleName();

    public static void xxx(Fragment fragment) {
        Retrofit retrofit = APIClient.getClient();
        JobGithubService service = retrofit.create(JobGithubService.class);
        Call<List<Position>> call = service.positions("java");
        call.enqueue(new Callback<List<Position>>() {
            @Override
            public void onResponse(Call<List<Position>> call, Response<List<Position>> response) {
                List<Position> body = response.body();
                for (Position position : body) {
                    Log.d(TAG, "onResponse - position: " + position);
                }

                ViewModelEx viewModelEx = ViewModelProviders.of(fragment).get(ViewModelEx.class);
                viewModelEx.addList(body);
            }

            @Override
            public void onFailure(Call<List<Position>> call, Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}
