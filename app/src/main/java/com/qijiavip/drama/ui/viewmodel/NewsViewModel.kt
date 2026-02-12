package com.qijiavip.drama.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qijiavip.drama.data.remote.api.NewsCategory
import com.qijiavip.drama.data.remote.api.NewsItem
import com.qijiavip.drama.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    
    private val _categories = MutableStateFlow<List<NewsCategory>>(emptyList())
    val categories: StateFlow<List<NewsCategory>> = _categories.asStateFlow()
    
    private val _newsList = MutableStateFlow<List<NewsItem>>(emptyList())
    val newsList: StateFlow<List<NewsItem>> = _newsList.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private var currentPage = 1
    private var currentCategoryId: Int? = null
    
    init {
        loadCategories()
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            newsRepository.getCategories()
                .onSuccess { _categories.value = it }
                .onFailure { /* 处理错误 */ }
        }
    }
    
    fun loadNews(categoryId: Int? = null, refresh: Boolean = false) {
        if (refresh) {
            currentPage = 1
            currentCategoryId = categoryId
        }
        
        viewModelScope.launch {
            _isLoading.value = true
            newsRepository.getNewsList(categoryId, currentPage, 20)
                .onSuccess { result ->
                    if (refresh) {
                        _newsList.value = result.list
                    } else {
                        _newsList.value = _newsList.value + result.list
                    }
                    currentPage++
                }
                .onFailure { /* 处理错误 */ }
            _isLoading.value = false
        }
    }
}
