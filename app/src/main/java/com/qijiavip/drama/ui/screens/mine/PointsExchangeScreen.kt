package com.qijiavip.drama.ui.screens.mine

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
fun PointsExchangeScreen(onBack: () -> Unit) {
    var selectedOption by remember { mutableStateOf(1) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        TopAppBar(
            title = { Text("积分兑换") },
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
            Text("选择兑换金额", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "兑换需绑定支付宝账号，手续费将从兑换积分中扣除",
                fontSize = 13.sp,
                color = TextGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 3000积分选项
            ExchangeOption(
                points = "3000积分",
                fee = "手续费：3%（90积分）",
                result = "实际到账\n2910积分",
                isSelected = selectedOption == 0,
                onClick = { selectedOption = 0 }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 50000积分选项（选中）
            ExchangeOption(
                points = "50000积分",
                fee = "手续费：3%（1500积分）",
                result = "实际到账\n48500积分",
                isSelected = selectedOption == 1,
                onClick = { selectedOption = 1 }
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 5000000积分选项
            ExchangeOption(
                points = "5000000积分",
                fee = "手续费：3%（150000积分）",
                result = "实际到账\n4850000积分",
                isSelected = selectedOption == 2,
                onClick = { selectedOption = 2 }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 手续费说明
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("手续费说明", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "兑换手续费率为3%，将从应兑换的积分中直接扣除。请确保您的积分余额充足。",
                        fontSize = 13.sp,
                        color = TextGray,
                        lineHeight = 20.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(100.dp))
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
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TextDark
                )
            ) {
                Text("取消", fontSize = 15.sp)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                )
            ) {
                Text("确认兑换50000积分", fontSize = 15.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun ExchangeOption(
    points: String,
    fee: String,
    result: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .then(
                if (isSelected) Modifier.border(2.dp, Primary, RoundedCornerShape(12.dp))
                else Modifier
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFFF3E0) else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(points, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(fee, fontSize = 12.sp, color = TextGray)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text("实际到账", fontSize = 12.sp, color = TextGray)
                Text(
                    result.substringAfter("\n"),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            }
        }
    }
}
