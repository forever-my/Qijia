package com.qijiavip.drama.ui.screens.news

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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

    var showUnlockCard by remember { mutableStateOf(true) }

    LaunchedEffect(newsId) {
        viewModel.loadNewsDetail(newsId.toLong())
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
                            .then(
                                if (newsDetail!!.isLocked || !showUnlockCard) {
                                    Modifier.verticalScroll(rememberScrollState())
                                } else {
                                    Modifier
                                }
                            )
                            .padding(16.dp)
                    ) {

                        // 信息流广告
                        AdManager.FeedAdView(
                            adpid = Config.UNI_ADPID_FEED,
                            count = 1
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // 内容区域（始终显示完整内容）
                        com.qijiavip.drama.ui.screens.task.WebViewScreen(
                            url = newsDetail?.sourceUrl.toString(),
                            title = newsDetail!!.title,
                            onBack = onBack,
                            enableScroll = newsDetail!!.isLocked || !showUnlockCard
                        )
                    }
                    
                    // 未解锁时显示遮罩（移到Column外面）
                    if (!newsDetail!!.isLocked && showUnlockCard) {
                        androidx.compose.ui.platform.LocalConfiguration.current.let { config ->
                            val screenHeight = config.screenHeightDp.dp
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(screenHeight * 0.75f)
                                    .align(Alignment.BottomCenter),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                shape = RectangleShape
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0x00FFF5F0),
                                                    Color(0xFFFFF5F0)
                                                )
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        modifier = Modifier.padding(32.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "观看广告解锁全文",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF333333)
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
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
                                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text("观看广告", color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
