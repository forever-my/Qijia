package com.qijiavip.drama.ui.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qijiavip.drama.ad.AdManager
import com.qijiavip.drama.ui.theme.*
import com.qijiavip.drama.ui.viewmodel.NewsDetailViewModel
import com.qijiavip.drama.utils.Config

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    newsId: String,
    isUnlocked: Boolean = false,
    onBack: () -> Unit = {},
    onUnlock: () -> Unit = {},
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val activity = context as? android.app.Activity
    
    val newsDetail by viewModel.newsDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var remainingTime by remember { mutableStateOf(0) }
    var showUnlockCard by remember { mutableStateOf(true) }

    LaunchedEffect(newsId) {
        viewModel.loadNewsDetail(newsId.toLong())
    }

    // 倒计时逻辑
    LaunchedEffect(newsDetail?.unlockExpireTime) {
        newsDetail?.unlockExpireTime?.let { expireTime ->
            while (true) {
                val remaining = ((expireTime - System.currentTimeMillis()) / 1000).toInt()
                if (remaining <= 0) break
                remainingTime = remaining
                kotlinx.coroutines.delay(1000)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 顶部导航栏
        TopAppBar(
            title = { Text("资讯详情") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ),
            windowInsets = WindowInsets(0, 0, 0, 0)
        )

        // 内容区域
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Primary
                    )
                }

                error != null -> {
                    Text(
                        text = error ?: "加载失败",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Red
                    )
                }

                newsDetail != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp)
                    ) {
                        // 标题
                        Text(
                            text = newsDetail!!.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // 元信息
                        Text(
                            text = "${newsDetail!!.categoryName ?: ""} · ${newsDetail!!.createdAt ?: ""} · ${newsDetail!!.readCount}阅读",
                            fontSize = 12.sp,
                            color = TextGray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (newsDetail!!.isLocked || !showUnlockCard) {
                            // 已解锁 - 显示内容
                                // 使用WebViewScreen加载HTML链接
                                com.qijiavip.drama.ui.screens.task.WebViewScreen(
                                    url = newsDetail?.sourceUrl.toString(),
                                    title = newsDetail!!.title,
                                    onBack = { /* 内嵌WebView不需要返回 */ }
                                )
                        } else {
                            // 未解锁 - 显示摘要
                            Text(
                                text = newsDetail!!.getDisplayContent().take(100) + "...",
                                fontSize = 15.sp,
                                color = TextGray,
                                lineHeight = 24.sp
                            )

                            Spacer(modifier = Modifier.height(40.dp))

                            // 解锁卡片
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF5F0)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "🔒",
                                        fontSize = 48.sp
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        text = "免费阅读",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = "今日剩余 5 次免费阅读",
                                        fontSize = 14.sp,
                                        color = TextGray
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))

                                    Button(
                                        onClick = {
                                            activity?.let { act ->
                                                val rewardAd = AdManager.RewardAd(
                                                    activity = act,
                                                    adpid = Config.UNI_ADPID,
                                                    userId = "user_${System.currentTimeMillis()}"
                                                )
                                                rewardAd.load(
                                                    onLoaded = {
                                                        rewardAd.show(
                                                            onReward = { 
                                                                // 广告播放完成，调用解锁接口
                                                                viewModel.unlockNews(newsId.toLong()) {
                                                                    showUnlockCard = false
                                                                    onUnlock()
                                                                }
                                                            },
                                                            onClose = {
                                                                rewardAd.destroy()
                                                            }
                                                        )
                                                    }
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                        shape = RoundedCornerShape(24.dp)
                                    ) {
                                        Text(
                                            text = "立即阅读",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // 倒计时悬浮按钮（已解锁时显示）
            if (newsDetail != null && newsDetail!!.isLocked && remainingTime > 0) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(60.dp),
                    shape = CircleShape,
                    shadowElevation = 8.dp,
                    color = Color.White
                ) {
                    Box(
                        modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp)
                            ) {
                                CircularProgressIndicator(
                                    progress = remainingTime / 60f,
                                    modifier = Modifier.fillMaxSize(),
                                    color = Primary,
                                    strokeWidth = 3.dp
                                )
                                Text(
                                    text = "$remainingTime\n秒",
                                    fontSize = 12.sp,
                                    color = Primary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Center),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }


