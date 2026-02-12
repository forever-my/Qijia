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
import com.qijiavip.drama.data.model.BannerItem
import com.qijiavip.drama.ui.theme.*
import com.qijiavip.drama.ui.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    onNewsClick: (String, Boolean) -> Unit,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val newsList by viewModel.newsList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    val banners = listOf(BannerItem("1", "", "邀请好友赚积分"))
    val listState = rememberLazyListState()
    
    LaunchedEffect(selectedCategoryId) {
        viewModel.loadNews(selectedCategoryId, refresh = true)
    }
    
    // 上拉加载更多
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastIndex ->
                if (lastIndex != null && lastIndex >= newsList.size - 2 && !isLoading) {
                    viewModel.loadNews(selectedCategoryId, refresh = false)
                }
            }
    }
    
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
        
        item {
            BannerCarousel(banners = banners)
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
        
        items(newsList) { news ->
            NewsItemCard(
                news = news,
                onClick = { onNewsClick(news.id.toString(), !news.isLocked) }
                )
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
@Composable
fun BannerCarousel(banners: List<BannerItem>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Primary)
    ) {
        Text(
            text = "邀请好友赚金币",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp)
        )
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
