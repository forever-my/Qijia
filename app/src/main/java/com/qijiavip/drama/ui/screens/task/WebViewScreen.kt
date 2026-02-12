package com.qijiavip.drama.ui.screens.task

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.webkit.WebSettings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.web.*
import com.qijiavip.drama.ui.theme.Primary

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    url: String,
    title: String = "福利中心",
    onBack: () -> Unit
) {
    val state = rememberWebViewState(url)
    val navigator = rememberWebViewNavigator()
    val context = androidx.compose.ui.platform.LocalContext.current
    
    LaunchedEffect(url) {
        Log.d("WebViewScreen", "Loading URL: $url")
    }
    
    BackHandler(enabled = navigator.canGoBack) {
        navigator.navigateBack()
    }
    
    BackHandler(enabled = !navigator.canGoBack) {
        onBack()
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        WebView(
            state = state,
            navigator = navigator,
            modifier = Modifier.fillMaxSize(),
            onCreated = { webView ->
                webView.settings.apply {
                    javaScriptEnabled = true
                    defaultTextEncodingName = "UTF-8"
                    cacheMode = WebSettings.LOAD_NO_CACHE
                    builtInZoomControls = false
                    useWideViewPort = true
                    loadWithOverviewMode = true
                    setSupportZoom(false)
                    @Suppress("DEPRECATION")
                    pluginState = WebSettings.PluginState.ON
                    domStorageEnabled = true
                    loadsImagesAutomatically = true
                    
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    }
                }
                
                // 注入JavaScript接口
                webView.addJavascriptInterface(object {
                    @android.webkit.JavascriptInterface
                    fun exit() {
                        (context as? android.app.Activity)?.runOnUiThread {
                            onBack()
                        }
                    }
                }, "partyMethod")
            }
        )
        
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Primary
            )
        }
    }
}

@Composable
private fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    androidx.activity.compose.BackHandler(enabled = enabled, onBack = onBack)
}

