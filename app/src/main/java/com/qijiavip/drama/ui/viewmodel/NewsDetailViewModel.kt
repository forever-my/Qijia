package com.qijiavip.drama.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qijiavip.drama.data.remote.api.NewsDetail
import com.qijiavip.drama.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    
    private val _newsDetail = MutableStateFlow<NewsDetail?>(null)
    val newsDetail: StateFlow<NewsDetail?> = _newsDetail.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    fun loadNewsDetail(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            repository.getNewsDetail(id)
                .onSuccess { _newsDetail.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }
    
    fun unlockNews(id: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.unlockNews(id)
                .onSuccess { 
                    loadNewsDetail(id)
                    onSuccess()
                }
                .onFailure { _error.value = it.message }
        }
    }
    
    fun readComplete(id: Long) {
        viewModelScope.launch {
            repository.readComplete(id)
        }
    }
}
