package com.qijiavip.drama.data.remote.interceptor

import com.qijiavip.drama.data.local.SessionManager
import com.qijiavip.drama.ui.navigation.NavigationManager
import com.qijiavip.drama.ui.navigation.Routes
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val sessionManager: SessionManager,
    private val navigationManager: NavigationManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization") != null) {
            runBlocking {
                sessionManager.clearSession()
                navigationManager.navigate(Routes.LOGIN, popUpToRoute = Routes.LOGIN, inclusive = true)
            }
        }
        return null
    }
}
