package com.qijiavip.drama.ui.screens.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qijiavip.drama.ui.theme.*
import com.qijiavip.drama.ui.viewmodel.MineViewModel

@Composable
fun MineScreen(
    onNavigateToFrozenPoints: () -> Unit = {},
    onNavigateToPendingRelease: () -> Unit = {},
    onNavigateToPointsHistory: () -> Unit = {},
    onNavigateToPointsExchange: () -> Unit = {},
    onNavigateToWithdraw: () -> Unit = {},
    onNavigateToCustomerService: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    viewModel: MineViewModel = hiltViewModel()
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }
    
    var showVipDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column {
                        Text(
                            text = userProfile?.nickname ?: "会员昵称",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "ID: ${userProfile?.inviteCode ?: "Q353JLT"}",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .clickable { showVipDialog = true }
                        ) {
                            repeat(userProfile?.memberLevel ?: 1) {
                                Text(text = "⭐", fontSize = 12.sp)
                            }
                            Text(
                                text = userProfile?.memberLevelName ?: "会员等级说明",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier.padding(start = 4.dp)
                            )
                            Text(text = "›", fontSize = 16.sp, color = Color.White)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PointsCard(
                        title = "冻结积分明细",
                        value = "${userProfile?.frozenPoints ?: 0}",
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToFrozenPoints
                    )
                    PointsCard(
                        title = "待释放明细",
                        value = "${userProfile?.pendingPoints ?: 0}",
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToPendingRelease
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PointsCard(
                        title = "积分明细",
                        value = "${userProfile?.points ?: 0}",
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToPointsHistory
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .clickable { onNavigateToPointsExchange() },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "积分兑换",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                            Text(text = "→", fontSize = 20.sp, color = Primary)
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActivityCard(
                value = "39",
                title = "我的活跃度",
                subtitle = "去提升活跃度",
                color = Color(0xFFFFE0B2),
                textColor = Primary,
                modifier = Modifier.weight(1f)
            )
            ActivityCard(
                value = "85",
                title = "团队活跃度",
                subtitle = "去邀请好友",
                color = Color(0xFFBBDEFB),
                textColor = Color(0xFF2196F3),
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Primary)
                .clickable { }
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "我的团长积分：59700000",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(text = "→", fontSize = 24.sp, color = Color.White)
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🔗", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "分享拿积分",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDark
                    )
                }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("邀请好友", fontSize = 13.sp)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column {
                SimpleMenuItem(icon = "🎁", title = "我的提现", onClick = onNavigateToWithdraw)
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
                SimpleMenuItem(icon = "🎧", title = "联系客服", onClick = onNavigateToCustomerService)
                HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
                SimpleMenuItem(icon = "⚙️", title = "设置", onClick = onNavigateToSettings)
            }
        }
        
        Spacer(modifier = Modifier.height(80.dp))
    }
    
    if (showVipDialog) {
        VipLevelDialog(onDismiss = { showVipDialog = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VipLevelDialog(onDismiss: () -> Unit) {
    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                Text(
                    "会员等级体系",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "等级根据邀请好友数量升级，不同等级享有不同权益",
                fontSize = 13.sp,
                color = TextGray,
                lineHeight = 20.sp
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            val levels = listOf(
                VipLevel(
                    name = "普通会员",
                    stars = "⭐",
                    requirement = "邀请0-4人",
                    benefits = listOf(
                        "好友看广告收益：10%分成",
                        "任务奖励：基础倍率",
                        "提现速度：标准（10元以下秒到）",
                        "积分释放：基础释放速度",
                        "每日看广告上限：4000积分"
                    )
                ),
                VipLevel(
                    name = "铜牌会员",
                    stars = "⭐⭐",
                    requirement = "邀请5-19人",
                    benefits = listOf(
                        "好友看广告收益：15%分成",
                        "任务奖励：1.1倍",
                        "提现速度：优先处理",
                        "积分释放：提升20%释放速度",
                        "每日看广告上限：5000积分",
                        "专属客服支持"
                    )
                ),
                VipLevel(
                    name = "银牌会员",
                    stars = "⭐⭐⭐",
                    requirement = "邀请20-49人",
                    benefits = listOf(
                        "好友看广告收益：20%分成",
                        "任务奖励：1.2倍",
                        "提现速度：快速通道",
                        "积分释放：提升40%释放速度",
                        "每日看广告上限：6000积分",
                        "专属客服支持",
                        "每月额外奖励500积分"
                    )
                ),
                VipLevel(
                    name = "金牌会员",
                    stars = "⭐⭐⭐⭐",
                    requirement = "邀请50-99人",
                    benefits = listOf(
                        "好友看广告收益：25%分成",
                        "任务奖励：1.3倍",
                        "提现速度：极速通道（30元以上优先审核）",
                        "积分释放：提升60%释放速度",
                        "每日看广告上限：8000积分",
                        "专属客服支持",
                        "每月额外奖励1000积分",
                        "团队管理工具（查看团队数据）"
                    )
                ),
                VipLevel(
                    name = "钻石会员",
                    stars = "⭐⭐⭐⭐⭐",
                    requirement = "邀请100人以上",
                    benefits = listOf(
                        "好友看广告收益：30%分成",
                        "任务奖励：1.5倍",
                        "提现速度：秒到（所有金额免审核）",
                        "积分释放：提升100%释放速度",
                        "每日看广告上限：10000积分",
                        "专属客服支持",
                        "每月额外奖励2000积分",
                        "团队管理工具（查看团队数据）",
                        "平台分红权益"
                    )
                )
            )
            
            levels.forEach { level ->
                VipLevelCard(level)
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Text("我知道了", fontSize = 15.sp, color = Color.White)
            }
        }
    }

}

data class VipLevel(
    val name: String,
    val stars: String,
    val requirement: String,
    val benefits: List<String>
)

@Composable
fun VipLevelCard(level: VipLevel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(level.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text(level.stars, fontSize = 14.sp)
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text("邀请要求：${level.requirement}", fontSize = 12.sp, color = TextGray)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("专属权益：", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextDark)
            
            level.benefits.forEach { benefit ->
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text("• ", fontSize = 12.sp, color = Primary)
                    Text(benefit, fontSize = 12.sp, color = TextGray, lineHeight = 18.sp)
                }
            }
        }
    }
}

@Composable
fun PointsCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.3f))
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "ⓘ", fontSize = 12.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun ActivityCard(
    value: String,
    title: String,
    subtitle: String,
    color: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            color = Primary
        )
    }
}

@Composable
fun MenuItem(
    icon: String,
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFE0B2)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 20.sp)
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
                    text = subtitle,
                    fontSize = 12.sp,
                    color = TextGray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
        Text(text = "›", fontSize = 24.sp, color = TextGray)
    }
}

@Composable
fun SimpleMenuItem(
    icon: String,
    title: String,
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )
        }
        Text(text = "›", fontSize = 24.sp, color = TextGray)
    }
}
