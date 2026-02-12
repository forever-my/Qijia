package com.qijiavip.drama.ui.screens.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
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
fun PendingReleaseScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        TopAppBar(
            title = { Text("待释放明细") },
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
                    Icon(Icons.Default.CheckCircle, "筛选", tint = TextDark)
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
            // 待释放总览卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("待释放总览", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("待释放总额", fontSize = 12.sp, color = TextGray)
                            Text("1352.25", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("累计释放", fontSize = 12.sp, color = TextGray)
                            Text("328.50", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Primary)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("释放进度", fontSize = 13.sp, color = TextDark)
                        Text("35%", fontSize = 13.sp, color = Primary, fontWeight = FontWeight.Bold)
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LinearProgressIndicator(
                        progress = { 0.35f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = Primary,
                        trackColor = Color(0xFFFFE0B2)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 待释放记录
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
                        Text("待释放记录", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text("最新优先 ▼", fontSize = 13.sp, color = TextGray)
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("时间", fontSize = 13.sp, color = TextGray, modifier = Modifier.weight(2f))
                        Text("金额", fontSize = 13.sp, color = TextGray, modifier = Modifier.weight(1f))
                        Text("说明", fontSize = 13.sp, color = TextGray, modifier = Modifier.weight(1f))
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    val records = listOf(
                        Triple("2026 02 03 09:33:44", "+0.25", "签到奖励"),
                        Triple("2025 09 05 22:11:25", "+0.47", "签到奖励"),
                        Triple("2025 04 11 20:16:38", "+39.9", "打卡任务奖励"),
                        Triple("2025 04 05 22:11:54", "+0.41", "签到奖励"),
                        Triple("2025 04 05 00:11:47", "-0.34255", "个人活跃释放"),
                        Triple("2025 04 05 13:11:66", "-0.552255", "团队活跃释放")
                    )
                    
                    records.forEach { (time, amount, desc) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(time, fontSize = 12.sp, color = TextDark, modifier = Modifier.weight(2f))
                            Text(
                                amount,
                                fontSize = 13.sp,
                                color = if (amount.startsWith("+")) Color(0xFF4CAF50) else Color(0xFFF44336),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            Text(desc, fontSize = 12.sp, color = TextGray, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
