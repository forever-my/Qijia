package com.qijiavip.drama.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qijiavip.drama.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInScreen(
    onBack: () -> Unit,
    onNavigateToTaskDetail: () -> Unit,
    onNavigateToTaskInfo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        TopAppBar(
            title = { Text("连续打卡") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            windowInsets = WindowInsets(0, 0, 0, 0)
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // 顶部渐变背景区域
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF7C4DFF), Color(0xFF9575FF))
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Column {
                    Text(
                        text = "每日打卡挑战",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "完成任务，轻松赢取奖励",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            
            // 查看我的活动按钮
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-20).dp)
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToTaskDetail() }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🔍", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "查看我的活动",
                            fontSize = 15.sp,
                            color = Primary
                        )
                    }
                }
            }
            
            // 我的打卡进度卡片
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "我的打卡进度",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Text(
                            text = "连续打卡3天",
                            fontSize = 14.sp,
                            color = TextGray
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // 进度条
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "当前进度",
                            fontSize = 12.sp,
                            color = TextGray,
                            modifier = Modifier.width(60.dp)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp)
                                .background(Color(0xFFEEEEEE), RoundedCornerShape(4.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.43f)
                                    .background(Primary, RoundedCornerShape(4.dp))
                            )
                        }
                        Text(
                            text = "3/7天",
                            fontSize = 12.sp,
                            color = TextDark,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // 补打卡提示
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🎁", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "出现补打卡请及时处理",
                                fontSize = 13.sp,
                                color = TextDark
                            )
                        }
                        Text(
                            text = "去查看",
                            fontSize = 13.sp,
                            color = Primary,
                            modifier = Modifier.clickable { }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 打卡7天任务
            CheckInTaskCard(
                days = "7",
                reward = "¥29.9",
                tasks = listOf(
                    "每天成功签到",
                    "每天浏览资讯5条",
                    "每天查看收益广告3次",
                    "活动期间内购入团长币1次"
                ),
                onDetailClick = onNavigateToTaskInfo
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 打卡5天任务
            CheckInTaskCard(
                days = "5",
                reward = "¥19.9",
                tasks = listOf(
                    "总邀请好友数量5个",
                    "每天点击首页banner广告2次",
                    "每天视频领奖每时长3分钟"
                ),
                onDetailClick = onNavigateToTaskInfo
            )
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun CheckInTaskCard(
    days: String,
    reward: String,
    tasks: List<String>,
    onDetailClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            if (days == "7") Color(0xFFE3F2FD) else Color(0xFFFFEBEE),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "打卡${days}天",
                        fontSize = 14.sp,
                        color = if (days == "7") Color(0xFF2196F3) else Color(0xFFF44336),
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "奖励$reward",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "任务要求",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            tasks.forEach { task ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("✓", fontSize = 12.sp, color = TextGray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = task,
                        fontSize = 13.sp,
                        color = TextGray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onDetailClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Primary)
            ) {
                Text(
                    text = "查看任务详情",
                    fontSize = 15.sp,
                    color = Primary
                )
            }
        }
    }
}
