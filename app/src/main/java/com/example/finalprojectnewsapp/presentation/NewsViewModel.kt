package com.example.finalprojectnewsapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectnewsapp.data.model.Article
import com.example.finalprojectnewsapp.data.model.NewsData
import com.example.finalprojectnewsapp.domain.NewsRepository
import com.example.finalprojectnewsapp.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val repository: NewsRepository
) : ViewModel() {

    private val _news: MutableLiveData<UIState<NewsData>> = MutableLiveData(UIState.Loading())
    val news: LiveData<UIState<NewsData>> = _news

    private val _everything: MutableLiveData<UIState<NewsData>> = MutableLiveData(UIState.Loading())
    val everything: LiveData<UIState<NewsData>> = _everything

    private val _search: MutableLiveData<String> = MutableLiveData("bitcoin")
    val search: LiveData<String> = _search

    init {
        getTopHeadlines("us")
    }

    fun getTopHeadlines(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNews(country)
                .collect { response ->
                    _news.postValue(response)
                }
        }
    }

    fun getEverything(query: String) {
        _search.postValue(query)
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEverything(query)
                .collect { response ->
                    _everything.postValue(response)
                }
        }

    }
}