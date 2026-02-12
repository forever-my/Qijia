package com.qijiavip.drama.data.remote.api

import com.qijiavip.drama.data.remote.dto.ApiResponse
import com.qijiavip.drama.data.remote.dto.LoginResult
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    
    @POST("auth/wechat/login")
    suspend fun wechatLogin(
        @Query("code") code: String,
        @Query("nickname") nickname: String? = null,
        @Query("avatar") avatar: String? = null
    ): ApiResponse<LoginResult>
    
    @POST("auth/logout")
    suspend fun logout(): ApiResponse<Unit>
}
