package com.example.app.com.example.app.github;

import com.example.app.com.example.app.github.data.model.job_list.Position;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
https://jobs.github.com/api
*/
public interface JobGithubService {

    /*
        https://jobs.github.com/positions.json?description=python&full_time=true&location=sf&page=1
     */
    @GET("positions.json?full_time=true&location=sf&page=1")
    Call<List<Position>> positions(@Query("description") String description);

}