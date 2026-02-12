package com.qijiavip.drama.ui.screens.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qijiavip.drama.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointsHistoryScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        TopAppBar(
            title = { Text("金币明细") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Refresh, "日历", tint = TextDark)
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Refresh, "筛选", tint = TextDark)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
            windowInsets = WindowInsets(0, 0, 0, 0)
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 金币明细表卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("金币明细表", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp))
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("可兑换金币", fontSize = 12.sp, color = TextGray)
                            Text("165241", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        }
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("累计兑换", fontSize = 12.sp, color = TextGray)
                            }
                            Text("5000000", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 金币变动明细
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("金币变动明细", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Icon(Icons.Default.Refresh, "刷新", tint = TextGray, modifier = Modifier.size(20.dp))
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("日期时间", fontSize = 13.sp, color = TextGray, modifier = Modifier.weight(2f))
                        Text("金币数量", fontSize = 13.sp, color = TextGray, modifier = Modifier.weight(1f))
                        Text("明细", fontSize = 13.sp, color = TextGray, modifier = Modifier.weight(1.5f))
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    val records = listOf(
                        Triple("2022-06-14 23:33", "564", "看广告奖励"),
                        Triple("2022-06-14 23:34", "55", "看广告奖励"),
                        Triple("2022-06-14 23:35", "150", "好友看广告奖励"),
                        Triple("2022-06-14 23:36", "2500", "好友累计提现奖励"),
                        Triple("2022-06-14 23:37", "15400", "好友累计提现奖励"),
                        Triple("2022-06-14 23:38", "-5000000", "余额兑换"),
                        Triple("2022-06-14 23:39", "65741", "看广告奖励"),
                        Triple("2022-06-14 23:40", "600", "累积邀请好友奖励"),
                        Triple("2022-06-14 23:41", "", ""),
                        Triple("2022-06-14 23:42", "", "")
                    )
                    
                    records.forEach { (time, amount, desc) ->
                        if (time.isNotEmpty()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(time, fontSize = 12.sp, color = TextDark, modifier = Modifier.weight(2f))
                                if (amount.isNotEmpty()) {
                                    Text(
                                        amount,
                                        fontSize = 13.sp,
                                        color = if (amount.startsWith("-")) Color(0xFFF44336) else Color(0xFF4CAF50),
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(desc, fontSize = 12.sp, color = TextGray, modifier = Modifier.weight(1.5f))
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Refresh, "加载", tint = TextGray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("加载更多", fontSize = 13.sp, color = TextGray)
                    }
                }
            }
        }
    }
}
