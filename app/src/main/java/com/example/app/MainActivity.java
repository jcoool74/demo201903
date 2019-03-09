package com.example.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.app.com.example.app.github.APIClient;
import com.example.app.com.example.app.github.JobGithubService;
import com.example.app.com.example.app.github.data.model.job_list.Position;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "_MainActivity_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test();
        startActivity(new Intent(this, ListActivity.class));
    }

    private void test() {
        Retrofit retrofit = APIClient.getClient();
        JobGithubService service = retrofit.create(JobGithubService.class);
        Call<List<Position>> call = service.positions("java");
        call.enqueue(new Callback<List<Position>>() {
            @Override
            public void onResponse(Call<List<Position>> call, Response<List<Position>> response) {
                Log.d(TAG, "sldkjlskjf");
            }

            @Override
            public void onFailure(Call<List<Position>> call, Throwable t) {
                Log.d(TAG, "22222");

            }
        });
    }
}
