package com.example.finalprojectnewsapp.data.datasource

import com.example.finalprojectnewsapp.data.model.NewsData
import retrofit2.Response

interface NewsDataSource {
  suspend fun getNewsHeadline(country:String):Response<NewsData>

  suspend fun getEverything(query:String):Response<NewsData>
}