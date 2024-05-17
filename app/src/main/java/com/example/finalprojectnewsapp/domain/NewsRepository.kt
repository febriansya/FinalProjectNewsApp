package com.example.finalprojectnewsapp.domain

import com.example.finalprojectnewsapp.data.model.NewsData
import com.example.finalprojectnewsapp.data.datasource.NewsDataSource
import com.example.finalprojectnewsapp.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val dataSource: NewsDataSource
) {

    suspend fun getNews(country: String): Flow<UIState<NewsData>> {
        return flow {
            emit(UIState.Loading())
            val data = dataSource.getNewsHeadline(country = country)
            if (data.isSuccessful && data.body() != null) {
                emit(UIState.Success(data.body()!!))
            } else {
                emit(UIState.Error(code = 1, message = "Something went wrong"))
            }
        }.catch {
            emit(UIState.Error(code = 1, message = it.message.toString() ?: "something error"))
        }
    }

    suspend fun getEverything(query: String): Flow<UIState<NewsData>> {
        return flow {
            emit(UIState.Loading())
            val data = dataSource.getEverything(query = query)
            if (data.isSuccessful && data.body() != null) {
                emit(UIState.Success(data.body()!!))
            } else {
                emit(UIState.Error(code = 1, message = "Something went wrong"))
            }
        }
    }
}