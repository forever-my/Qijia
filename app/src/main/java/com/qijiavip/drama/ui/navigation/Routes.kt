package com.qijiavip.drama.ui.navigation

import android.net.Uri

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val MAIN = "main"
    const val HOME = "home"
    const val VIDEO = "video"
    const val TASK = "task"
    const val MINE = "mine"
    const val NEWS_DETAIL = "news_detail/{newsId}/{isUnlocked}"
    const val INVITE = "invite"
    const val CHECK_IN = "check_in"
    const val TASK_DETAIL = "task_detail"
    const val TASK_INFO = "task_info"
    const val CHECK_IN_RECORD = "check_in_record"
    const val FROZEN_POINTS = "frozen_points"
    const val PENDING_RELEASE = "pending_release"
    const val POINTS_HISTORY = "points_history"
    const val POINTS_EXCHANGE = "points_exchange"
    const val WITHDRAW = "withdraw"
    const val CUSTOMER_SERVICE = "customer_service"
    const val SETTINGS = "settings"
    const val WEBVIEW = "webview/{url}/{title}"
    
    fun newsDetail(newsId: String, isUnlocked: Boolean) = "news_detail/$newsId/$isUnlocked"
    fun webview(url: String, title: String = "福利中心") = "webview/${Uri.encode(url)}/${Uri.encode(title)}"
}
