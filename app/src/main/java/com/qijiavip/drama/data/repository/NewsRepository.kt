package com.qijiavip.drama.data.repository

import com.qijiavip.drama.data.remote.api.NewsApi
import com.qijiavip.drama.data.remote.api.NewsCategory
import com.qijiavip.drama.data.remote.api.NewsDetail
import com.qijiavip.drama.data.remote.api.NewsItem
import com.qijiavip.drama.data.remote.dto.PageResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi
) {
    suspend fun getCategories(): Result<List<NewsCategory>> {
        return try {
            val response = newsApi.getCategories()
            if (response.code == 0 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.msg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getNewsList(categoryId: Int?, page: Int, size: Int): Result<PageResult<NewsItem>> {
        return try {
            val response = newsApi.getNewsList(categoryId, page, size)
            if (response.code == 0 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.msg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getNewsDetail(id: Long): Result<NewsDetail> {
        return try {
            val response = newsApi.getNewsDetail(id)
            if (response.code == 0 && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.msg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun unlockNews(id: Long): Result<Unit> {
        return try {
            val response = newsApi.unlockNews(id)
            if (response.code == 0) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.msg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun readComplete(id: Long): Result<Unit> {
        return try {
            val response = newsApi.readComplete(id)
            if (response.code == 0) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.msg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
