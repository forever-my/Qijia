package com.qijiavip.drama.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.qijiavip.drama.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInRecordScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        TopAppBar(
            title = { Text("打卡记录") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
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
            // Banner广告位
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF2C2C2C)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "BANNER广告位",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 打卡进度
            Text(
                "打卡第3天，还差3天",
                fontSize = 14.sp,
                color = TextDark,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 日历卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // 月份切换
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("上月", fontSize = 14.sp, color = TextGray)
                        Text("下月", fontSize = 14.sp, color = TextGray)
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // 日历网格
                    val days = listOf(
                        DayInfo(1, DayStatus.CHECKED),
                        DayInfo(2, DayStatus.CHECKED),
                        DayInfo(3, DayStatus.CURRENT),
                        DayInfo(4, DayStatus.NORMAL),
                        DayInfo(5, DayStatus.NORMAL),
                        DayInfo(6, DayStatus.MAKEUP),
                        DayInfo(7, DayStatus.NORMAL)
                    )
                    
                    Column {
                        for (row in 0..4) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                for (col in 0..6) {
                                    val dayNum = row * 7 + col + 1
                                    if (dayNum <= 31) {
                                        val dayInfo = days.find { it.day == dayNum } ?: DayInfo(dayNum, DayStatus.NORMAL)
                                        DayCell(dayInfo)
                                    } else {
                                        Spacer(modifier = Modifier.size(44.dp))
                                    }
                                }
                            }
                            if (row < 4) Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 任务列表
            val tasks = listOf(
                "看指定页面广告还差2次" to "去完成",
                "签到次数还差1次" to "去签到",
                "看广告赚金币数还差1万金币" to "去完成",
                "点击软件内任务BANNER广告还差4次" to "去完成",
                "点击播单广告还差1次" to "去完成",
                "点击互推盒子广告还差2次" to "去完成"
            )
            
            tasks.forEach { (task, action) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        task,
                        fontSize = 14.sp,
                        color = Primary,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "$action >>",
                        fontSize = 14.sp,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Medium
                    )
                }
                if (task != tasks.last().first) {
                    Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                }
            }
        }
    }
}

data class DayInfo(val day: Int, val status: DayStatus)

enum class DayStatus {
    NORMAL, CHECKED, CURRENT, MAKEUP
}

@Composable
fun DayCell(dayInfo: DayInfo) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(
                when (dayInfo.status) {
                    DayStatus.CHECKED -> Color(0xFF4CAF50)
                    DayStatus.CURRENT -> Color(0xFFF44336)
                    DayStatus.MAKEUP -> Color.Transparent
                    DayStatus.NORMAL -> Color(0xFFF5F5F5)
                }
            )
            .then(
                if (dayInfo.status == DayStatus.MAKEUP) {
                    Modifier.border(1.dp, Primary, CircleShape)
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                dayInfo.day.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = when (dayInfo.status) {
                    DayStatus.CHECKED, DayStatus.CURRENT -> Color.White
                    DayStatus.MAKEUP -> TextDark
                    DayStatus.NORMAL -> TextGray
                }
            )
            if (dayInfo.status == DayStatus.MAKEUP) {
                Text(
                    "补牌\n19.9",
                    fontSize = 8.sp,
                    color = Primary,
                    lineHeight = 10.sp
                )
            }
        }
    }
}
