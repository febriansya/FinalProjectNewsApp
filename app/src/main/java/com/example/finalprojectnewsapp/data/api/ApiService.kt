package com.example.finalprojectnewsapp.data.api

import com.example.finalprojectnewsapp.BuildConfig
import com.example.finalprojectnewsapp.data.model.NewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<NewsData>

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<NewsData>
}