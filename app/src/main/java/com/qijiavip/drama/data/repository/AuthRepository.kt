package com.qijiavip.drama.data.repository

import com.qijiavip.drama.data.local.SessionManager
import com.qijiavip.drama.data.remote.api.AuthApi
import com.qijiavip.drama.data.remote.dto.LoginResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val sessionManager: SessionManager
) {
    
    suspend fun wechatLogin(code: String, nickname: String? = null, avatar: String? = null): Result<LoginResult> {
        return try {
            android.util.Log.d("AuthRepository", "调用API: code=$code")
            val response = authApi.wechatLogin(code, nickname, avatar)
            android.util.Log.d("AuthRepository", "API响应: code=${response.code}, msg=${response.msg}")
            if (response.code == 0 && response.data != null) {
                // 保存token和用户信息
                sessionManager.saveToken(response.data.token)
                sessionManager.saveUserId(response.data.userId)
                android.util.Log.d("AuthRepository", "Token已保存")
                Result.success(response.data)
            } else {
                android.util.Log.e("AuthRepository", "登录失败: ${response.msg}")
                Result.failure(Exception(response.msg))
            }
        } catch (e: Exception) {
            android.util.Log.e("AuthRepository", "网络异常: ${e.message}", e)
            Result.failure(e)
        }
    }
    
    suspend fun logout(): Result<Unit> {
        return try {
            val response = authApi.logout()
            if (response.code == 0) {
                sessionManager.clearSession()
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.msg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
