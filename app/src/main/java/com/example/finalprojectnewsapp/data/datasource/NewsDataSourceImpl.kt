package com.example.finalprojectnewsapp.data.datasource

import com.example.finalprojectnewsapp.data.model.NewsData
import com.example.finalprojectnewsapp.data.api.ApiService
import retrofit2.Response
import javax.inject.Inject


class NewsDataSourceImpl @Inject constructor(
    val apiService: ApiService
) : NewsDataSource {

    override suspend fun getNewsHeadline(country: String): Response<NewsData> {
        return apiService.getNews(country)
    }

    override suspend fun getEverything(query: String): Response<NewsData> {
        return apiService.getEverything(query = query)
    }
}
