package com.qijiavip.drama.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qijiavip.drama.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(onBack: () -> Unit, onNavigateToCheckInRecord: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        TopAppBar(
            title = { Text("任务详情") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ),
            windowInsets = WindowInsets(0, 0, 0, 0)
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 任务标题和状态
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "7天打卡挑战",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateToCheckInRecord,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Text("📅", fontSize = 20.sp)
                    }
                    
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFE8F5E9)
                    ) {
                        Text(
                            text = "进行中",
                            fontSize = 12.sp,
                            color = Color(0xFF4CAF50),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 奖励和天数标签
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFFF9C4)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🎁", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "奖励 ¥29.9",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF57C00)
                        )
                    }
                }
                
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFE3F2FD)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("📅", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "打卡7天",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // 当前进度
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "当前进度",
                    fontSize = 14.sp,
                    color = TextGray
                )
                Text(
                    text = "2/7天",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 进度条
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color(0xFFEEEEEE), RoundedCornerShape(4.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.29f)
                        .background(Primary, RoundedCornerShape(4.dp))
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 任务要求
            Text(
                text = "任务要求",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 任务列表
            TaskRequirementItem(
                title = "每天成功签到",
                progress = "今日已完成",
                isCompleted = true
            )
            
            TaskRequirementItem(
                title = "每天浏览资讯5条",
                progress = "今日已完成",
                isCompleted = true
            )
            
            TaskRequirementItem(
                title = "每天查看收益广告3次",
                progress = "今日进度: 1/3次",
                isCompleted = false
            )
            
            TaskRequirementItem(
                title = "活动期间内购入团长币1次",
                progress = "未完成",
                isCompleted = false
            )
            
            TaskRequirementItem(
                title = "每天点击首页banner广告2次",
                progress = "今日进度: 0/2次",
                isCompleted = false
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 任务说明
            Text(
                text = "任务说明",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "1. 每日任务需当天24点前完成，超时将无法计入当日进度\n2. 所有任务完成后，奖励将在24小时内发放至您的账户\n3. 如有任务异常，请联系客服处理",
                fontSize = 13.sp,
                color = TextGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
    
    // 底部按钮
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFF44336)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF44336))
            ) {
                Text(
                    text = "放弃任务",
                    fontSize = 15.sp
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                )
            ) {
                Text(
                    text = "去完成今日任务",
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun TaskRequirementItem(
    title: String,
    progress: String,
    isCompleted: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (isCompleted) "✓" else "☐",
            fontSize = 20.sp,
            color = if (isCompleted) Color(0xFF4CAF50) else Color(0xFFBDBDBD)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = TextDark
            )
            Text(
                text = progress,
                fontSize = 12.sp,
                color = if (isCompleted) Color(0xFF4CAF50) else TextGray,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
