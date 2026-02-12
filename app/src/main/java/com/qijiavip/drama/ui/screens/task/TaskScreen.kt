package com.qijiavip.drama.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qijiavip.drama.ui.theme.*

@Composable
fun TaskScreen(
    onNavigateToInvite: () -> Unit = {},
    onNavigateToCheckIn: () -> Unit = {},
    onNavigateToWelfare: () -> Unit = {},
    onNavigateToTaoJin: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Primary)
        )
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .offset(y = (-30).dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "下次签到时间",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("签到拿现金", fontSize = 14.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Box(
                    modifier = Modifier
                        .background(Color(0xFF5A5A5A), RoundedCornerShape(6.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "19:30-20:30",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(7) { index ->
                        SignInDayItem(
                            day = index + 1,
                            isChecked = index < 3,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "已签3次，让生活更美好",
                        fontSize = 12.sp,
                        color = TextGray
                    )
                    Text(
                        text = "签到规则",
                        fontSize = 12.sp,
                        color = Primary
                    )
                }
            }
        }
        
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(16.dp)
                    .background(Primary)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "每日任务",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column {
                TaskItem(
                    icon = "📺",
                    title = "看广告赚积分",
                    buttonText = "还剩4次",
                    onClick = { }
                )
                
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
                
                TaskItem(
                    icon = "✓",
                    title = "连续打卡",
                    buttonText = "去接任务",
                    onClick = onNavigateToCheckIn
                )
                
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
                
                TaskItem(
                    icon = "🔗",
                    title = "分享拿积分",
                    buttonText = "邀请好友",
                    onClick = { onNavigateToInvite() }
                )
                
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
                
                TaskItem(
                    icon = "🎁",
                    title = "福利中心",
                    buttonText = "去拿福利",
                    onClick = onNavigateToWelfare
                )
                
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
                
                TaskItem(
                    icon = "💰",
                    title = "游动积分",
                    buttonText = "去完成",
                    onClick = onNavigateToTaoJin
                )
            }
        }
    }
}

@Composable
fun SignInDayItem(
    day: Int,
    isChecked: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    if (isChecked) Primary else Color.Transparent,
                    CircleShape
                )
                .border(
                    width = 1.dp,
                    color = if (isChecked) Primary else Color(0xFFE0E0E0),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isChecked) {
                Text(
                    text = "✓",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (isChecked) "已签" else "未签",
            fontSize = 11.sp,
            color = if (isChecked) Primary else TextGray
        )
    }
}

@Composable
fun TaskItem(
    icon: String,
    title: String,
    badge: String? = null,
    buttonText: String,
    isDisabled: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextDark
                )
                if (badge != null) {
                    Text(
                        text = badge,
                        fontSize = 11.sp,
                        color = Color(0xFFFF6B6B),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
        
        Button(
            onClick = onClick,
            enabled = !isDisabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                disabledContainerColor = Color(0xFFE0E0E0)
            ),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = buttonText,
                fontSize = 13.sp,
                color = if (isDisabled) TextGray else Color.White
            )
        }
    }
}
