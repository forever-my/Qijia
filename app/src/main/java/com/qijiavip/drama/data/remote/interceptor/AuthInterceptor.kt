package com.qijiavip.drama.data.remote.interceptor

import android.util.Log
import com.qijiavip.drama.data.local.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    private val noAuthEndpoints = setOf(
        "login",
        "register"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        Log.d("AuthInterceptor", "Request URL: ${originalRequest.url}")

        val isAuthEndpoint = noAuthEndpoints.none { endpoint ->
            originalRequest.url.toString().contains(endpoint, ignoreCase = true)
        }

        if (isAuthEndpoint) {
            val token = runBlocking {
                sessionManager.getToken()
            }
            
            Log.d("AuthInterceptor", "Token: ${token?.take(20)}...")

            if (token != null) {
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                Log.d("AuthInterceptor", "Added Authorization header")
                return chain.proceed(newRequest)
            } else {
                Log.w("AuthInterceptor", "Token is null, no Authorization header added")
            }
        } else {
            Log.d("AuthInterceptor", "No auth required for this endpoint")
        }

        return chain.proceed(originalRequest)
    }
}
