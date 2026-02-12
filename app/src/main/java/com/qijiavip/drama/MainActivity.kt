package com.qijiavip.drama

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.qijiavip.drama.data.local.SessionManager
import com.qijiavip.drama.ui.screens.MainScreen
import com.qijiavip.drama.ui.screens.auth.LoginScreen
import com.qijiavip.drama.ui.theme.QijiaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    
    @Inject
    lateinit var sessionManager: SessionManager
    
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // 权限请求结果
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 设置状态栏透明，启用沉浸式
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = 
                android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        // 请求必要权限
        requestPermissions()
        
        setContent {
            QijiaTheme {
                var isLoggedIn by remember { mutableStateOf<Boolean?>(null) }
                
                LaunchedEffect(Unit) {
                    val token = sessionManager.getToken()
                    isLoggedIn = !token.isNullOrEmpty()
                }
                
                when (isLoggedIn) {
                    true -> MainScreen(onLogout = { isLoggedIn = false })
                    false -> LoginScreen(onLoginSuccess = { isLoggedIn = true })
                    null -> {} // 加载中
                }
            }
        }
    }
    
    private fun requestPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        
        val needRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        
        if (needRequest.isNotEmpty()) {
            permissionLauncher.launch(needRequest.toTypedArray())
        }
    }
}
