package com.example.app.restful;

import com.example.app.Position;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
https://jobs.github.com/api
*/
public interface WebService {

    /*
        https://jobs.github.com/positions.json?description=python&full_time=true&location=sf&page=1
     */
    @GET("positions.json?full_time=true&location=sf&page=1")
    Call<List<Position>> getPositions(@Query("description") String description);

}