package com.qijiavip.drama.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
fun TaskInfoScreen(onBack: () -> Unit, onAcceptTask: () -> Unit) {
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
            // 顶部说明
            Text(
                text = "每日签到有好礼，完成任务赢取现金奖励",
                fontSize = 14.sp,
                color = TextDark,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 提示框
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFFF3E0)
            ) {
                Text(
                    text = "请仔细阅读以下任务要求，接受任务后需在活动期间完成所有任务",
                    fontSize = 13.sp,
                    color = Color(0xFFF57C00),
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 任务完成要求
            Text(
                text = "任务完成要求",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 任务列表
            TaskInfoItem(
                icon = "?",
                iconColor = Color(0xFF9C27B0),
                title = "总邀请好友数量",
                desc = "活动期间累计邀请5位好友成功注册"
            )
            
            TaskInfoItem(
                icon = "✓",
                iconColor = Color(0xFF4CAF50),
                title = "每天成功签到",
                desc = "活动期间累计完成3天签到"
            )
            
            TaskInfoItem(
                icon = "📰",
                iconColor = Color(0xFFE91E63),
                title = "每天浏览资讯",
                desc = "每天浏览5条资讯内容，每条停留至少30秒"
            )
            
            TaskInfoItem(
                icon = "📺",
                iconColor = Color(0xFFFF9800),
                title = "每天查看收益广告",
                desc = "每天成功看3条收益广告，每条广告需完整观看"
            )
            
            TaskInfoItem(
                icon = "🎯",
                iconColor = Color(0xFFF44336),
                title = "每天点击首页Banner广告",
                desc = "每天点击首页Banner广告2次，每次停留至少10秒"
            )
            
            TaskInfoItem(
                icon = "📱",
                iconColor = Color(0xFF2196F3),
                title = "每天点击榜单下播提广告",
                desc = "每天点击榜单下方播提广告2次，完成有效观察"
            )
            
            TaskInfoItem(
                icon = "🎬",
                iconColor = Color(0xFF00BCD4),
                title = "每天观看视频时长",
                desc = "每天累计观看视频时长达到5分钟"
            )
            
            TaskInfoItem(
                icon = "💰",
                iconColor = Color(0xFFFF4081),
                title = "活动期间内购入团长币",
                desc = "活动期间成功购入团长币至少1次"
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 活动规则
            Text(
                text = "活动规则",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "1. 所有任务需在活动期间内完成，超时将无法获得奖励",
                fontSize = 13.sp,
                color = TextGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
    
    // 底部按钮
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = onAcceptTask,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            )
        ) {
            Text(
                text = "去接受任务",
                fontSize = 15.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun TaskInfoItem(
    icon: String,
    iconColor: Color,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(iconColor.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                fontSize = 16.sp,
                color = iconColor
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )
            Text(
                text = desc,
                fontSize = 12.sp,
                color = TextGray,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
