package com.qijiavip.drama.data.remote.dto

data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    val data: T?
)

data class LoginResult(
    val token: String,
    val userId: Long,
    val nickname: String,
    val avatar: String,
    val memberLevel: Int,
    val isNewUser: Boolean
)
