package com.qijiavip.drama.ui.screens.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
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
fun FrozenPointsScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        TopAppBar(
            title = { Text("冻结金币明细") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Notifications, "日历", tint = TextDark)
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
            // 待解冻金币明细卡片
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
                        Text("待解冻金币明细", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text("全部时间 ▼", fontSize = 13.sp, color = Primary)
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("总冻结金币", fontSize = 12.sp, color = TextGray)
                            Text("10300", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("已解冻", fontSize = 12.sp, color = TextGray)
                            Text("1360", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("未达标", fontSize = 12.sp, color = TextGray)
                            Text("8940", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF44336))
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 新人看广告奖励
            RewardSection(
                title = "新人看广告奖励",
                headers = listOf("累计看广", "奖励金币", "奖励"),
                rows = listOf(
                    listOf("20", "50", "已解冻", Color(0xFF4CAF50)),
                    listOf("100", "300", "未达标", Color(0xFFF44336)),
                    listOf("500", "500", "未达标", Color(0xFFF44336)),
                    listOf("1500", "1000", "未达标", Color(0xFFF44336))
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 累计邀请好友奖励
            RewardSection(
                title = "累计邀请好友奖励",
                headers = listOf("人数", "奖励金币", "奖励"),
                rows = listOf(
                    listOf("5", "200", "已解冻", Color(0xFF4CAF50)),
                    listOf("10", "300", "未达标", Color(0xFFF44336)),
                    listOf("50", "1000", "未达标", Color(0xFFF44336))
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 好友看广告奖励
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("好友看广告奖励", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("好友账号：5656需合适在仍要在人", fontSize = 13.sp, color = TextGray)
                    Text("好友注册日期2025-02-03", fontSize = 13.sp, color = TextGray)
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("数量", fontSize = 13.sp, color = TextGray, modifier = Modifier.weight(1f))
                        Text("奖励金币", fontSize = 13.sp, color = TextGray, modifier = Modifier.weight(1f))
                        Text("状态", fontSize = 13.sp, color = TextGray, modifier = Modifier.weight(1f))
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("5", fontSize = 14.sp, color = TextDark, modifier = Modifier.weight(1f))
                        Text("50", fontSize = 14.sp, color = TextDark, modifier = Modifier.weight(1f))
                        Text("已解冻", fontSize = 14.sp, color = Color(0xFF4CAF50), modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun RewardSection(
    title: String,
    headers: List<String>,
    rows: List<List<Any>>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                headers.forEach { header ->
                    Text(header, fontSize = 13.sp, color = TextGray, modifier = Modifier.weight(1f))
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            rows.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(row[0] as String, fontSize = 14.sp, color = TextDark, modifier = Modifier.weight(1f))
                    Text(row[1] as String, fontSize = 14.sp, color = TextDark, modifier = Modifier.weight(1f))
                    Text(row[2] as String, fontSize = 14.sp, color = row[3] as Color, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
