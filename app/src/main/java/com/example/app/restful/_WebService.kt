package com.example.app.restful

import com.example.app.model.JobPosting

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
https://jobs.github.com/api
*/
interface _WebService {

    /*
        https://jobs.github.com/positions.json?description=python&full_time=true&location=sf&page=1
     */
    @GET("positions.json?full_time=true&location=sf&page=1")
    fun getList(@Query("description") description: String): Call<List<JobPosting>>

    @GET("/positions/{id}.json")
    fun getId(@Path("id") id: String): Call<JobPosting>
}