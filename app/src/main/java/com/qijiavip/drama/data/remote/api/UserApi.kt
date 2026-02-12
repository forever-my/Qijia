package com.qijiavip.drama.data.remote.api

import com.qijiavip.drama.data.remote.dto.ApiResponse
import retrofit2.http.GET

interface UserApi {
    
    @GET("user/profile")
    suspend fun getProfile(): ApiResponse<UserProfile>
}

data class UserProfile(
    val userId: Long,
    val nickname: String?,
    val avatar: String?,
    val memberLevel: Int,
    val memberLevelName: String?,
    val points: Int,
    val frozenPoints: Int,
    val pendingPoints: Int,
    val teamCount: Int,
    val inviteCode: String?
)
