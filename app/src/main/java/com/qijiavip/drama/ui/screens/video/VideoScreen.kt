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
import androidx.paging.Config
import com.kwad.sdk.api.KsAdSDK
import com.kwad.sdk.api.KsContentPage
import com.kwad.sdk.api.KsScene
import com.qijiavip.drama.R
import com.qijiavip.drama.ui.theme.Primary
import com.qijiavip.drama.utils.Config

@Composable
fun VideoScreen() {
    var currentPoints by remember { mutableStateOf(0.0) }
    var videoProgress by remember { mutableStateOf(0f) }
    var isPlaying by remember { mutableStateOf(false) }
    var contentPage by remember { mutableStateOf<KsContentPage?>(null) }
    var currentPosition by remember { mutableStateOf(-1) } // 记录当前视频位置
    var hasEarnedPoints by remember { mutableStateOf(false) } // 记录当前视频是否已获得积分
    val pointsPerMinute = 5.0 // 1分钟5积分
    
    // 加载ContentPage
    LaunchedEffect(Unit) {
        while (!com.qijiavip.drama.QijiaApp.isSdkStarted) {
            kotlinx.coroutines.delay(100)
        }
        
        // 使用内容联盟posId
        val scene = KsScene.Builder(Config.KS_POSID).build()
        val page = KsAdSDK.getLoadManager().loadContentPage(scene)
        
        // 设置视频监听
        page.setVideoListener(object : KsContentPage.VideoListener {
            override fun onVideoPlayStart(item: KsContentPage.ContentItem?) {
                Log.i("VideoScreen", "Video play start")
                isPlaying = true
            }

            override fun onVideoPlayPaused(item: KsContentPage.ContentItem?) {
                Log.i("VideoScreen", "Video play paused")
                isPlaying = false
            }

            override fun onVideoPlayResume(item: KsContentPage.ContentItem?) {
                Log.i("VideoScreen", "Video play resume")
                isPlaying = true
            }

            override fun onVideoPlayCompleted(item: KsContentPage.ContentItem?) {
                Log.i("VideoScreen", "Video play completed")
                isPlaying = false
            }

            override fun onVideoPlayError(item: KsContentPage.ContentItem?, errorCode: Int, extra: Int) {
                Log.e("VideoScreen", "Video play error: $errorCode")
                isPlaying = false
            }
        })
        
        // 设置页面监听
        page.setPageListener(object : KsContentPage.PageListener {
            override fun onPageEnter(item: KsContentPage.ContentItem) {
                Log.i("VideoScreen", "Page enter: position=${item.position}")
                // 切换到新视频时重置进度和积分标记
                if (currentPosition != item.position) {
                    currentPosition = item.position
                    videoProgress = 0f
                    hasEarnedPoints = false
                    Log.i("VideoScreen", "New video, reset progress and points flag")
                }
            }

            override fun onPageResume(item: KsContentPage.ContentItem) {
                Log.i("VideoScreen", "Page resume: position=${item.position}")
            }

            override fun onPagePause(item: KsContentPage.ContentItem) {
                Log.i("VideoScreen", "Page pause: position=${item.position}")
                isPlaying = false
            }

            override fun onPageLeave(item: KsContentPage.ContentItem) {
                Log.i("VideoScreen", "Page leave: position=${item.position}")
            }
        })
        
        contentPage = page
        Log.i("VideoScreen", "ContentPage loaded")
    }
    
    // 计时逻辑：1分钟5积分，每个视频只能获得一次
    LaunchedEffect(isPlaying, hasEarnedPoints) {
        while (isPlaying && !hasEarnedPoints) {
            kotlinx.coroutines.delay(100) // 每0.1秒更新一次
            videoProgress += 0.1f / 60f // 60秒为1个周期
            
            if (videoProgress >= 1f) {
                currentPoints += pointsPerMinute
                hasEarnedPoints = true // 标记已获得积分
                Log.i("VideoScreen", "Earned points: $pointsPerMinute, total: $currentPoints")
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
                        
                        // 添加ContentPage的Fragment
                        (ctx as? FragmentActivity)?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(id, contentPage!!.fragment)
                            ?.commitAllowingStateLoss()
                        
                        Log.i("VideoScreen", "ContentPage fragment added")
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
