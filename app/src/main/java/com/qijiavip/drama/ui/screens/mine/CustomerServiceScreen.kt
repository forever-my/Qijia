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
fun CustomerServiceScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        TopAppBar(
            title = { Text("联系客服") },
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
            // 客服信息卡片
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("👨‍💼", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("在线客服", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("工作时间：9:00 - 18:00", fontSize = 13.sp, color = TextGray)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 常见问题
            Text("常见问题", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val faqs = listOf(
                "如何获得积分？" to "通过浏览资讯、观看视频、完成每日任务等方式可以获得积分奖励。",
                "积分如何提现？" to "在'我的提现'页面选择提现金额，完成解锁条件后即可提现到支付宝或微信。",
                "为什么积分被冻结？" to "新获得的积分会有冻结期，完成相应任务后会自动解冻。",
                "提现多久到账？" to "10元以下秒到账，30元及以上需要1-3个工作日审核。",
                "如何邀请好友？" to "点击'分享拿金币'，通过分享链接邀请好友注册即可获得奖励。"
            )
            
            faqs.forEach { (question, answer) ->
                var expanded by remember { mutableStateOf(false) }
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                question,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextDark,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                if (expanded) "▲" else "▼",
                                fontSize = 12.sp,
                                color = TextGray
                            )
                        }
                        
                        if (expanded) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                answer,
                                fontSize = 13.sp,
                                color = TextGray,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 联系方式
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("其他联系方式", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("客服邮箱：support@example.com", fontSize = 13.sp, color = TextGray)
                    Text("客服微信：customer_service", fontSize = 13.sp, color = TextGray)
                }
            }
        }
    }
}
