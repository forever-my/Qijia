package com.qijiavip.drama.ui.screens.video

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.kwad.sdk.api.KsAdSDK
import com.kwad.sdk.api.KsContentPage
import com.kwad.sdk.api.KsScene
import com.qijiavip.drama.R
import com.qijiavip.drama.ui.theme.Primary

@Composable
fun VideoScreenWithContentPage() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var currentPoints by remember { mutableStateOf(0.0) }
    var videoProgress by remember { mutableStateOf(0f) }
    var isPlaying by remember { mutableStateOf(false) }
    var contentPage by remember { mutableStateOf<KsContentPage?>(null) }
    val pointsPerVideo = 50.0 / 3
    
    // 加载内容联盟
    LaunchedEffect(Unit) {
        while (!com.qijiavip.drama.QijiaApp.isSdkStarted) {
            kotlinx.coroutines.delay(100)
        }
        
        // 内容联盟posId，需要替换成你的
        val scene = KsScene.Builder(4000000021L).build()
        val page = KsAdSDK.getLoadManager().loadContentPage(scene)
        
        // 设置监听
        page.setPageListener(object : KsContentPage.PageListener {
            override fun onPageEnter(item: KsContentPage.ContentItem) {
                Log.i("VideoScreen", "Page enter: ${item.position}")
            }

            override fun onPageResume(item: KsContentPage.ContentItem) {
                Log.i("VideoScreen", "Page resume: ${item.position}")
                isPlaying = true
            }

            override fun onPagePause(item: KsContentPage.ContentItem) {
                Log.i("VideoScreen", "Page pause: ${item.position}")
                isPlaying = false
            }

            override fun onPageLeave(item: KsContentPage.ContentItem) {
                Log.i("VideoScreen", "Page leave: ${item.position}")
            }
        })
        
        contentPage = page
        Log.i("VideoScreen", "ContentPage loaded")
    }
    
    // 计时逻辑
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            kotlinx.coroutines.delay(100)
            videoProgress += 0.01f
            
            if (videoProgress >= 1f) {
                currentPoints += pointsPerVideo
                videoProgress = 0f
            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        if (contentPage == null) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text("加载中...", color = Color.White, fontSize = 16.sp)
            }
        } else {
            AndroidView(
                factory = { ctx ->
                    FragmentContainerView(ctx).apply {
                        id = R.id.content_page_container
                        layoutParams = android.view.ViewGroup.LayoutParams(
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        
                        // 添加Fragment
                        (ctx as? FragmentActivity)?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(id, contentPage!!.fragment)
                            ?.commitAllowingStateLoss()
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // 圆形进度条
        Surface(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .offset(y = (-80).dp)
                .size(60.dp),
            shape = CircleShape,
            shadowElevation = 8.dp,
            color = Color.Black.copy(alpha = 0.6f)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { videoProgress.coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxSize(),
                    color = Primary,
                    strokeWidth = 3.dp,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
                Text(
                    text = String.format("%.1f", currentPoints),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
