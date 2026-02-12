package com.qijiavip.drama.ui.screens.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.window.Dialog
import com.qijiavip.drama.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteScreen(
    onBack: () -> Unit
) {
    var showInviteDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) } // 0=直推, 1=间推
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        // 顶部导航栏
        TopAppBar(
            title = { Text("邀请好友") },
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
        
        // 内容区域
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // 橙色邀请卡片
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFF8A65)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "邀请好友 赚取奖励",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "每邀请1位好友获得3000冻结积分",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // 邀请码框
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "我的邀请码",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            TextButton(
                                onClick = { /* 复制邀请码 */ },
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = Color.White,
                                    contentColor = Primary
                                ),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text("📋 复制", fontSize = 14.sp)
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // 立即邀请按钮
                    Button(
                        onClick = { showInviteDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Primary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "立即邀请好友",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
            
            // 统计数据
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    StatItem("0", "直推人数")
                    StatItem("0", "间推人数")
                    StatItem("0", "今日邀请")
                    StatItem("0", "累计奖励")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 邀请规则
            Text(
                text = "邀请规则",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    RuleItem(
                        number = "1",
                        title = "邀请好友注册",
                        desc = "好友通过您的邀请码注册成功",
                        reward = "+5000积分",
                        rewardColor = Primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    RuleItem(
                        number = "2",
                        title = "邀请好友看广告",
                        desc = "好友看广告您获得奖励",
                        reward = "最多100000积分",
                        rewardColor = Color(0xFFFF6B6B)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    RuleItem(
                        number = "3",
                        title = "邀请好友提现",
                        desc = "好友提现您获得奖励",
                        reward = "最多5000000积分",
                        rewardColor = Color(0xFFFF6B6B)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 邀请记录
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "邀请记录",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Row {
                    Text(
                        text = "直推",
                        fontSize = 14.sp,
                        color = if (selectedTab == 0) Primary else TextGray,
                        modifier = Modifier
                            .clickable { selectedTab = 0 }
                            .padding(horizontal = 8.dp)
                    )
                    Text(
                        text = "间推",
                        fontSize = 14.sp,
                        color = if (selectedTab == 1) Primary else TextGray,
                        modifier = Modifier
                            .clickable { selectedTab = 1 }
                            .padding(horizontal = 8.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
    
    // 邀请弹窗
    if (showInviteDialog) {
        InviteDialog(onDismiss = { showInviteDialog = false })
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextGray
        )
    }
}

@Composable
fun RuleItem(
    number: String,
    title: String,
    desc: String,
    reward: String,
    rewardColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Primary.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextDark
                )
                Text(
                    text = desc,
                    fontSize = 12.sp,
                    color = TextGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        Text(
            text = reward,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = rewardColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteDialog(onDismiss: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                // 海报预览区域
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color(0xFFFF8A65), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "齐加",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "齐加",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "让生活更美好",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        // 二维码占位
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // 保存海报按钮
                Button(
                    onClick = { /* 保存海报 */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6B6B)
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(
                        text = "保存邀请海报",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // 分享方式
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ShareItem("微信", "💬")
                    ShareItem("朋友圈", "🔄")
                    ShareItem("QQ", "🐧")
                    ShareItem("更多", "⋯")
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // 邀请方式说明
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "邀请好友方式",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("1  保存上方邀请海报", fontSize = 12.sp, color = Primary)
                    Text("2  分享给微信/QQ好友或朋友圈", fontSize = 12.sp, color = Primary)
                    Text("3  好友扫码注册后成为您的邀请用户", fontSize = 12.sp, color = Primary)
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

}

@Composable
fun ShareItem(label: String, icon: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(BackgroundGray, CircleShape)
                .clickable { /* 分享 */ },
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = TextGray
        )
    }
}
