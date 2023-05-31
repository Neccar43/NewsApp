package com.example.newsapp.service

import com.example.newsapp.model.News
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
//8961a58d-5487-42e7-9893-166a35e75c5d
//https://newsapi.org/v2/top-headlines?country=us&apiKey=API_KEY

    @GET("v2/top-headlines")
    fun getNews(@Query("country") country:String,@Query("page") pageNumberInt: Int=1,@Query("apiKey") apiKey:String): Call<News>

}