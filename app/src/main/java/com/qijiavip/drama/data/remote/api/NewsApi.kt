package com.qijiavip.drama.data.remote.api

import com.qijiavip.drama.data.remote.dto.ApiResponse
import com.qijiavip.drama.data.remote.dto.PageResult
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    
    @GET("news/categories")
    suspend fun getCategories(): ApiResponse<List<NewsCategory>>
    
    @GET("news/list")
    suspend fun getNewsList(
        @Query("categoryId") categoryId: Int? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ApiResponse<PageResult<NewsItem>>
    
    @GET("news/{id}")
    suspend fun getNewsDetail(@Path("id") id: Long): ApiResponse<NewsDetail>
    
    @POST("news/{id}/unlock")
    suspend fun unlockNews(@Path("id") id: Long): ApiResponse<Unit>
    
    @POST("news/{id}/read-complete")
    suspend fun readComplete(@Path("id") id: Long): ApiResponse<Unit>
}

data class NewsCategory(
    val id: Int,
    val name: String,
    val sort: Int
)

data class NewsItem(
    val id: Long,
    val title: String,
    val coverUrl: String?,
    val summary: String?,
    val categoryId: Int,
    val categoryName: String?,
    val readCount: Int,
    val isLocked: Boolean,
    val createdAt: String
)

data class NewsDetail(
    val id: Long,
    val title: String,
    @com.google.gson.annotations.SerializedName("coverUrl")
    val cover: String?,
    val summary: String?,
    val content: String?,
    val sourceUrl: String?,
    val categoryId: Int,
    val categoryName: String?,
    val readCount: Int,
    @com.google.gson.annotations.SerializedName("unlocked")
    val isLocked: Boolean = false,
    @com.google.gson.annotations.JsonAdapter(DateStringToLongAdapter::class)
    val unlockExpireTime: Long?,
    val unlockRemainSeconds: Int?,
    val createdAt: String? = null
) {
    // 获取显示内容：优先sourceUrl，其次content，最后summary
    fun getDisplayContent(): String = sourceUrl ?: content ?: summary ?: ""
    fun isHtmlUrl(): Boolean = sourceUrl?.startsWith("http") == true
}

class DateStringToLongAdapter : com.google.gson.TypeAdapter<Long?>() {
    override fun write(out: com.google.gson.stream.JsonWriter, value: Long?) {
        if (value == null) out.nullValue() else out.value(value)
    }
    
    override fun read(`in`: com.google.gson.stream.JsonReader): Long? {
        if (`in`.peek() == com.google.gson.stream.JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        val str = `in`.nextString()
        if (str.isEmpty()) return null
        return try {
            // 尝试解析ISO 8601日期字符串
            java.time.Instant.parse(str).toEpochMilli()
        } catch (e: Exception) {
            // 如果是纯数字，直接返回
            str.toLongOrNull()
        }
    }
}
