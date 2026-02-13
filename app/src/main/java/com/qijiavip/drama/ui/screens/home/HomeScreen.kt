package com.qijiavip.drama.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.qijiavip.drama.data.model.BannerItem
import com.qijiavip.drama.ui.theme.*
import com.qijiavip.drama.ui.viewmodel.NewsViewModel
import io.dcloud.ads.core.v2.feed.DCFeedAd

@Composable
fun HomeScreen(
    onNewsClick: (String, Boolean) -> Unit,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val newsList by viewModel.newsList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var elapsedSeconds by remember { mutableStateOf(0) }
    var isTimerActive by remember { mutableStateOf(false) }
    var lastScrollOffset by remember { mutableStateOf(0) }
    var feedAds by remember { mutableStateOf<List<DCFeedAd>>(emptyList()) }
    val banners = listOf(BannerItem("1", "", "邀请好友赚积分"))
    val listState = rememberLazyListState()
    val context = androidx.compose.ui.platform.LocalContext.current
    val activity = context as? android.app.Activity
    
    // 初始加载广告池
    LaunchedEffect(Unit) {
        if (activity != null) {
            repeat(5) {
                kotlinx.coroutines.delay(500)
                AdManager.loadFeedAds(
                    activity = activity,
                    adpid = com.qijiavip.drama.utils.Config.UNI_ADPID_FEED,
                    count = 1,
                    onLoaded = { ads -> feedAds = feedAds + ads }
                )
            }
        }
    }
    
    // 监听滚动，动态补充广告池
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.collect { index ->
            val usedAdCount = (index / 10) + 1
            val needMore = usedAdCount + 3 > feedAds.size
            if (needMore && activity != null) {
                AdManager.loadFeedAds(
                    activity = activity,
                    adpid = com.qijiavip.drama.utils.Config.UNI_ADPID_FEED,
                    count = 1,
                    onLoaded = { ads -> feedAds = feedAds + ads }
                )
            }
        }
    }

    LaunchedEffect(selectedCategoryId) {
        viewModel.loadNews(selectedCategoryId, refresh = true)
    }

    // 监听滚动，控制计时器
    LaunchedEffect(listState) {
        var lastMoveTime = System.currentTimeMillis()
        
        while (true) {
            kotlinx.coroutines.delay(100)
            
            val scrollOffset = listState.firstVisibleItemScrollOffset
            val isScrolling = scrollOffset != lastScrollOffset
            
            if (isScrolling) {
                lastMoveTime = System.currentTimeMillis()
            }
            
            val idleTime = System.currentTimeMillis() - lastMoveTime
            val isIdle = idleTime > 3000
            
            lastScrollOffset = scrollOffset
            
            // 未停止滑动超过3秒就计时
            isTimerActive = !isIdle
            
            // 上拉加载更多
            val lastIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            if (lastIndex != null && lastIndex >= newsList.size - 2 && !isLoading) {
                viewModel.loadNews(selectedCategoryId, refresh = false)
            }
        }
    }
    
    // 计时器（仅在广告可见时运行）
    LaunchedEffect(isTimerActive) {
        while (isTimerActive) {
            kotlinx.coroutines.delay(1000)
            elapsedSeconds++
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            item {
                CategoryTabs(
                    categories = categories,
                    selectedCategoryId = selectedCategoryId,
                    onCategorySelected = {
                        selectedCategoryId = it
                    }
                )
            }
            
            // 顶部广告
            if (feedAds.isNotEmpty()) {
                item(key = "ad_top") {
                    AdManager.SingleFeedAdView(feedAd = feedAds[0])
                }
            }

            if (isLoading && newsList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Primary)
                    }
                }
            }

            var adIndex = 1
            newsList.forEachIndexed { index, news ->
                item(key = "news_$index") {
                    NewsItemCard(
                        news = news,
                        onClick = { onNewsClick(news.id.toString(), !news.isLocked) }
                    )
                }
                
                // 每9条插入一个广告
                if ((index + 1) % 9 == 0 && adIndex < feedAds.size) {
                    val currentAd = feedAds[adIndex]
                    item(key = "ad_${adIndex}") {
                        AdManager.SingleFeedAdView(feedAd = currentAd)
                    }
                    adIndex++
                }
            }

            if (isLoading && newsList.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Primary, modifier = Modifier.size(24.dp))
                    }
                }
            }
        }

        // 右下角悬浮计时器
        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(64.dp),
            shape = CircleShape,
            color = Primary,
            shadowElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = if (elapsedSeconds < 60) {
                        "${elapsedSeconds}秒"
                    } else {
                        val minutes = elapsedSeconds / 60
                        val seconds = elapsedSeconds % 60
                        "${minutes}分${seconds}秒"
                    },
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable
fun NewsItemCard(
    news: com.qijiavip.drama.data.remote.api.NewsItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        coil.compose.AsyncImage(
            model = news.coverUrl,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp, 75.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CardBackground),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .height(75.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = news.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark,
                maxLines = 2
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${news.readCount}阅读",
                    fontSize = 12.sp,
                    color = TextGray
                )

                if (news.isLocked) {
                    Text(
                        text = "🔒 需解锁",
                        fontSize = 12.sp,
                        color = Primary
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryTabs(
    categories: List<com.qijiavip.drama.data.remote.api.NewsCategory>,
    selectedCategoryId: Int?,
    onCategorySelected: (Int?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        // "全部"选项
        item {
            val isSelected = selectedCategoryId == null
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) Primary else Color.Transparent)
                    .clickable { onCategorySelected(null) }
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "全部",
                    color = if (isSelected) Color.White else TextDark,
                    fontSize = 15.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        items(categories) { category ->
            val isSelected = category.id == selectedCategoryId
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) Primary else Color.Transparent)
                    .clickable { onCategorySelected(category.id) }
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = category.name,
                    color = if (isSelected) Color.White else TextDark,
                    fontSize = 15.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}
