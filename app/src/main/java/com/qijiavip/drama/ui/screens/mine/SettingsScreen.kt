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
fun SettingsScreen(onBack: () -> Unit, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        TopAppBar(
            title = { Text("设置") },
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
            // 账户设置
            Text("账户设置", fontSize = 14.sp, color = TextGray, modifier = Modifier.padding(horizontal = 4.dp))
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    SettingItem("个人信息", "修改昵称、头像等", onClick = {})
                    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 0.5.dp)
                    SettingItem("账号安全", "修改密码、绑定手机", onClick = {})
                    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 0.5.dp)
                    SettingItem("提现账户管理", "管理支付宝、微信账户", onClick = {})
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 通知设置
            Text("通知设置", fontSize = 14.sp, color = TextGray, modifier = Modifier.padding(horizontal = 4.dp))
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    var pushEnabled by remember { mutableStateOf(true) }
                    SettingSwitchItem("推送通知", "接收任务和奖励提醒", pushEnabled) { pushEnabled = it }
                    
                    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 0.5.dp)
                    
                    var soundEnabled by remember { mutableStateOf(true) }
                    SettingSwitchItem("声音提示", "播放提示音", soundEnabled) { soundEnabled = it }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 隐私设置
            Text("隐私设置", fontSize = 14.sp, color = TextGray, modifier = Modifier.padding(horizontal = 4.dp))
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    SettingItem("隐私政策", "查看隐私政策", onClick = {})
                    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 0.5.dp)
                    SettingItem("用户协议", "查看用户协议", onClick = {})
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // 其他
            Text("其他", fontSize = 14.sp, color = TextGray, modifier = Modifier.padding(horizontal = 4.dp))
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    SettingItem("关于我们", "版本 1.0.0", onClick = {})
                    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 0.5.dp)
                    SettingItem("清除缓存", "0 MB", onClick = {})
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 退出登录按钮
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336)
                )
            ) {
                Text("退出登录", fontSize = 15.sp, color = Color.White)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = TextDark)
            if (subtitle.isNotEmpty()) {
                Text(subtitle, fontSize = 12.sp, color = TextGray, modifier = Modifier.padding(top = 2.dp))
            }
        }
        Text("›", fontSize = 24.sp, color = TextGray)
    }
}

@Composable
fun SettingSwitchItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = TextDark)
            Text(subtitle, fontSize = 12.sp, color = TextGray, modifier = Modifier.padding(top = 2.dp))
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Primary
            )
        )
    }
}
