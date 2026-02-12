package com.qijiavip.drama.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.qijiavip.drama.ui.components.BottomNavigationBar
import com.qijiavip.drama.ui.navigation.Routes
import com.qijiavip.drama.ui.screens.home.HomeScreen
import com.qijiavip.drama.ui.screens.video.VideoScreen
import com.qijiavip.drama.ui.screens.task.*
import com.qijiavip.drama.ui.screens.mine.*
import com.qijiavip.drama.ui.screens.news.NewsDetailScreen
import com.qijiavip.drama.utils.DeviceIdUtil
import com.qijiavip.drama.utils.XuanShangWaSignUtil
import com.qijiavip.drama.utils.TaoJinSignUtil
@Preview
@Composable
fun MainScreen(onLogout: () -> Unit = {}) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Routes.HOME
    
    val showBottomBar = currentRoute in listOf(Routes.HOME, Routes.VIDEO, Routes.TASK, Routes.MINE)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Routes.HOME) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.HOME) { 
                HomeScreen(
                    onNewsClick = { newsId, isUnlocked ->
                        navController.navigate(Routes.newsDetail(newsId, isUnlocked))
                    }
                )
            }
            composable(Routes.VIDEO) { VideoScreen() }
            composable(Routes.TASK) { 
                TaskScreen(
                    onNavigateToInvite = { navController.navigate(Routes.INVITE) },
                    onNavigateToCheckIn = { navController.navigate(Routes.CHECK_IN) },
                    onNavigateToWelfare = {
                        // 生成悬赏蛙任务URL并跳转
                        val context = navController.context
                        val deviceId = DeviceIdUtil.getDeviceId(context)
                        val url = XuanShangWaSignUtil.generateTaskUrl(
                            userId = "user_1770790765043", // user_1770790123496  user_1770790178466  user_1770790765043  user_1770790788181 user_1770790801766
                            deviceId = deviceId,
                            imei1 = "", // Android 10+无法获取
                            imei2 = "",
                            oaid = deviceId, // 使用OAID
                            mobileModel = XuanShangWaSignUtil.getProcessedDeviceModel(),
                            sysVer = XuanShangWaSignUtil.getSystemVersion()
                        )
                        navController.navigate(Routes.webview(url, "福利中心"))
                    },
                    onNavigateToTaoJin = {
                        // 生成91淘金URL并跳转
                        val context = navController.context
                        val url = TaoJinSignUtil.generateUrl(
                            context = context,
                            userId = "user_1770790765043"
                        )
                        navController.navigate(Routes.webview(url, "游动积分"))
                    }
                )
            }
            composable(Routes.MINE) {
                MineScreen(
                    onNavigateToFrozenPoints = { navController.navigate(Routes.FROZEN_POINTS) },
                    onNavigateToPendingRelease = { navController.navigate(Routes.PENDING_RELEASE) },
                    onNavigateToPointsHistory = { navController.navigate(Routes.POINTS_HISTORY) },
                    onNavigateToPointsExchange = { navController.navigate(Routes.POINTS_EXCHANGE) },
                    onNavigateToWithdraw = { navController.navigate(Routes.WITHDRAW) },
                    onNavigateToCustomerService = { navController.navigate(Routes.CUSTOMER_SERVICE) },
                    onNavigateToSettings = { navController.navigate(Routes.SETTINGS) }
                )
            }
            
            composable(Routes.INVITE) {
                InviteScreen(onBack = { navController.popBackStack() })
            }
            
            composable(Routes.CHECK_IN) {
                CheckInScreen(
                    onBack = { navController.popBackStack() },
                    onNavigateToTaskDetail = { navController.navigate(Routes.TASK_DETAIL) },
                    onNavigateToTaskInfo = { navController.navigate(Routes.TASK_INFO) }
                )
            }
            
            composable(Routes.TASK_DETAIL) {
                TaskDetailScreen(
                    onBack = { navController.popBackStack() },
                    onNavigateToCheckInRecord = { navController.navigate(Routes.CHECK_IN_RECORD) }
                )
            }
            
            composable(Routes.CHECK_IN_RECORD) {
                CheckInRecordScreen(onBack = { navController.popBackStack() })
            }
            
            composable(Routes.TASK_INFO) {
                TaskInfoScreen(
                    onBack = { navController.popBackStack() },
                    onAcceptTask = { navController.navigate(Routes.TASK_DETAIL) }
                )
            }
            
            composable(Routes.FROZEN_POINTS) {
                FrozenPointsScreen(onBack = { navController.popBackStack() })
            }
            
            composable(Routes.PENDING_RELEASE) {
                PendingReleaseScreen(onBack = { navController.popBackStack() })
            }
            
            composable(Routes.POINTS_HISTORY) {
                PointsHistoryScreen(onBack = { navController.popBackStack() })
            }
            
            composable(Routes.POINTS_EXCHANGE) {
                PointsExchangeScreen(onBack = { navController.popBackStack() })
            }
            
            composable(Routes.WITHDRAW) {
                WithdrawScreen(
                    onBack = { navController.popBackStack() },
                    onBindAccount = { /* TODO: 绑定账户 */ }
                )
            }
            
            composable(Routes.CUSTOMER_SERVICE) {
                CustomerServiceScreen(onBack = { navController.popBackStack() })
            }
            
            composable(Routes.SETTINGS) {
                val authViewModel: com.qijiavip.drama.ui.viewmodel.AuthViewModel = hiltViewModel()
                SettingsScreen(
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        authViewModel.logout()
                        onLogout()
                    }
                )
            }
            
            composable(
                route = Routes.WEBVIEW,
                arguments = listOf(
                    navArgument("url") { type = NavType.StringType },
                    navArgument("title") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val url = backStackEntry.arguments?.getString("url")?.let { Uri.decode(it) } ?: ""
                val title = backStackEntry.arguments?.getString("title")?.let { Uri.decode(it) } ?: "福利中心"
                WebViewScreen(
                    url = url,
                    title = title,
                    onBack = { navController.popBackStack() }
                )
            }
            
            composable(
                route = Routes.NEWS_DETAIL,
                arguments = listOf(
                    navArgument("newsId") { type = NavType.StringType },
                    navArgument("isUnlocked") { type = NavType.BoolType }
                )
            ) { backStackEntry ->
                val newsId = backStackEntry.arguments?.getString("newsId") ?: ""
                val isUnlocked = backStackEntry.arguments?.getBoolean("isUnlocked") ?: false
                val viewModel: com.qijiavip.drama.ui.viewmodel.NewsDetailViewModel = androidx.hilt.navigation.compose.hiltViewModel()
                
                NewsDetailScreen(
                    newsId = newsId,
                    isUnlocked = isUnlocked,
                    onBack = { navController.popBackStack() },
                    onUnlock = {
                        // TODO: 播放广告，成功后调用解锁接口
                        viewModel.unlockNews(newsId.toLong()) {
                            // 解锁成功
                        }
                    }
                )
            }
        }
    }
}
