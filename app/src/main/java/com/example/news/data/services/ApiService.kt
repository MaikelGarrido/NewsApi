package com.example.news.data.services

import com.example.news.data.model.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines?country=ve")
    fun topPost(@Query("apiKey") apiKey: String) : Call<ResponseNews>


}