package com.qijiavip.drama.ui.screens.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qijiavip.drama.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawScreen(onBack: () -> Unit, onBindAccount: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        TopAppBar(
            title = { Text("我的提现") },
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
            // 可提现金额
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("可提现金额", fontSize = 14.sp, color = TextGray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "¥165.24",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("可用积分：165241", fontSize = 12.sp, color = TextGray)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 提现金额选项
            Text("选择提现金额", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val withdrawOptions = listOf(
                "0.3元" to "3200积分",
                "1元" to "10200积分",
                "5元" to "51000积分",
                "10元" to "102000积分",
                "30元" to "306000积分",
                "50元" to "510000积分"
            )
            
            withdrawOptions.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { (amount, points) ->
                        WithdrawOptionCard(
                            amount = amount,
                            points = points,
                            modifier = Modifier.weight(1f),
                            onClick = {}
                        )
                    }
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 绑定提现账户
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onBindAccount)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("提现账户", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark)
                        Text("未绑定", fontSize = 12.sp, color = TextGray)
                    }
                    Text("去绑定 ›", fontSize = 14.sp, color = Primary, fontWeight = FontWeight.Medium)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 提现记录
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("提现记录", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text("查看全部 ›", fontSize = 13.sp, color = TextGray)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("暂无提现记录", fontSize = 14.sp, color = TextGray, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 提现说明
            Text("提现说明", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark)
            Spacer(modifier = Modifier.height(8.dp))
            Text("• 10元以下提现秒到，无需审核", fontSize = 12.sp, color = TextGray, lineHeight = 18.sp)
            Text("• 30元及以上需要人工审核，1-3个工作日到账", fontSize = 12.sp, color = TextGray, lineHeight = 18.sp)
            Text("• 提现需完成解锁条件：解锁指定资讯数和停留时长", fontSize = 12.sp, color = TextGray, lineHeight = 18.sp)
            Text("• 每日提现次数有限，请合理安排", fontSize = 12.sp, color = TextGray, lineHeight = 18.sp)
        }
    }
}

@Composable
fun WithdrawOptionCard(
    amount: String,
    points: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(amount, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Primary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(points, fontSize = 12.sp, color = TextGray)
        }
    }
}
