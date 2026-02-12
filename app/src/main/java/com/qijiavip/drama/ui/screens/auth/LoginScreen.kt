package com.qijiavip.drama.ui.screens.auth

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qijiavip.drama.ui.theme.Primary
import com.qijiavip.drama.ui.theme.TextGray
import com.qijiavip.drama.ui.viewmodel.AuthViewModel
import com.qijiavip.drama.ui.viewmodel.LoginState
import com.qijiavip.drama.utils.WeChatLoginUtil
import com.qijiavip.drama.wxapi.WXEntryActivity

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isWeChatInstalled = remember { WeChatLoginUtil.isWeChatInstalled() }
    val loginState by viewModel.loginState.collectAsState()
    
    // 设置微信登录回调
    DisposableEffect(Unit) {
        Log.d("LoginScreen", "设置微信登录回调")
        WXEntryActivity.onLoginResult = { code, errCode ->
            Log.d("LoginScreen", "收到微信登录结果: code=$code, errCode=$errCode")
            if (errCode == 0 && code != null) {
                Log.d("LoginScreen", "开始调用登录接口")
                viewModel.wechatLogin(code)
            } else {
                val msg = when (errCode) {
                    -2 -> "用户取消登录"
                    -4 -> "用户拒绝授权"
                    else -> "登录失败"
                }
                android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_SHORT).show()
            }
        }
        
        onDispose {
            Log.d("LoginScreen", "清除微信登录回调")
            WXEntryActivity.onLoginResult = null
        }
    }
    
    // 监听登录状态
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                onLoginSuccess()
                viewModel.resetState()
            }
            is LoginState.Error -> {
                Toast.makeText(context, (loginState as LoginState.Error).message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(120.dp))
            
            // Logo
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .background(Primary, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "齐加",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "让生活更美好",
                fontSize = 14.sp,
                color = TextGray
            )
            Text(
                text = "FORABETTERLIFE",
                fontSize = 12.sp,
                color = TextGray,
                letterSpacing = 1.sp
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // 温馨提示
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text(
                    text = "温馨提示：",
                    fontSize = 13.sp,
                    color = Color(0xFFFF6B6B),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "齐加APP不对任何会员进行收费",
                    fontSize = 13.sp,
                    color = Color(0xFFFF6B6B)
                )
                Text(
                    text = "看广告收益提现秒到",
                    fontSize = 13.sp,
                    color = Color(0xFFFF6B6B)
                )
            }
            
            // 微信登录按钮
            Button(
                onClick = {
                    if (isWeChatInstalled) {
                        WeChatLoginUtil.login()
                    } else {
                        Toast.makeText(context, "请先安装微信客户端", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = loginState !is LoginState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF07C160)
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                if (loginState is LoginState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "💬", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "微信快捷登录",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(80.dp))
        }
        
        // 底部协议
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = "用户协议",
                    fontSize = 12.sp,
                    color = Primary
                )
                Text(
                    text = "  |  ",
                    fontSize = 12.sp,
                    color = TextGray
                )
                Text(
                    text = "隐私政策",
                    fontSize = 12.sp,
                    color = Primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "云南齐加壹站信息技术有限公司版权所有",
                fontSize = 11.sp,
                color = TextGray,
                textAlign = TextAlign.Center
            )
        }}}


