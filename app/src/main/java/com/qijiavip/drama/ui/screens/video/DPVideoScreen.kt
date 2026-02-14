package com.qijiavip.drama.ui.screens.video

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import com.bytedance.sdk.dp.DPSdk
import com.bytedance.sdk.dp.DPWidgetDrawParams
import com.bytedance.sdk.dp.IDPDrawListener
import com.bytedance.sdk.dp.IDPWidget
import com.qijiavip.drama.R

@Composable
fun DPVideoScreen() {
    val context = LocalContext.current
    val activity = context as? FragmentActivity
    var dpWidget by remember { mutableStateOf<IDPWidget?>(null) }
    var isSDKReady by remember { mutableStateOf(false) }
    
    // 等待SDK启动成功
    LaunchedEffect(Unit) {
        Log.d("DPVideoScreen", "等待SDK启动...")
        while (!DPSdk.isStartSuccess()) {
            kotlinx.coroutines.delay(100)
        }
        isSDKReady = true
        Log.d("DPVideoScreen", "SDK已启动，准备创建小视频组件")
    }
    
    DisposableEffect(Unit) {
        onDispose {
            dpWidget?.fragment?.onDestroy()
        }
    }
    
    if (isSDKReady && activity != null) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                FragmentContainerView(ctx).apply {
                    id = R.id.video_container
                    
                    // 创建参数（SDK>=2600会自动从配置文件读取代码位）
                    val params = DPWidgetDrawParams.obtain()
                        .adOffset(280)
                        .drawContentType(DPWidgetDrawParams.DRAW_CONTENT_TYPE_ONLY_VIDEO)  // 改为混排模式
                        .hideClose(false, null)
                        .adListener(object : com.bytedance.sdk.dp.IDPAdListener() {
                            override fun onDPAdShow(map: Map<String, Any>?) {
                                Log.d("DPVideoScreen", "✅ 广告展示: $map")
                            }


                            override fun onSkippedVideo(map: Map<String, Any>?) {
                                Log.d("DPVideoScreen", "✅ 广告跳过: $map")
                            }

                            override fun onDPAdRequest(map: Map<String?, Any?>?) {
                                Log.d("DPVideoScreen", "📡 广告请求: $map")
                            }

                            override fun onDPAdRequestSuccess(map: Map<String?, Any?>?) {
                                Log.d("DPVideoScreen", "✅ 广告请求成功: $map")
                            }

                            override fun onDPAdFillFail(map: Map<String?, Any?>?) {
                                Log.e("DPVideoScreen", "❌ 广告填充失败: $map")
                            }
                        })
                        .listener(object : IDPDrawListener() {
                            override fun onDPRefreshFinish() {
                                Log.d("DPVideoScreen", "🔄 刷新完成")
                            }

                            override fun onDPPageChange(position: Int) {
                                Log.d("DPVideoScreen", "📄 页面切换: position=$position")
                            }

                            override fun onDPVideoPlay(map: Map<String, Any>) {
                                Log.d("DPVideoScreen", "▶️ 视频播放: $map")
                            }

                            override fun onDPVideoCompletion(map: Map<String, Any>) {
                                Log.d("DPVideoScreen", "✅ 视频播放完成: $map")
                            }

                            override fun onDPVideoOver(map: Map<String, Any>) {
                                Log.d("DPVideoScreen", "🏁 视频结束: $map")
                            }

                            override fun onDPClose() {
                                Log.d("DPVideoScreen", "❌ 关闭")
                            }

                            override fun onDPRequestStart(map: Map<String, Any>?) {
                                Log.d("DPVideoScreen", "📡 开始请求内容")
                            }

                            override fun onDPRequestSuccess(list: List<Map<String, Any>>) {
                                Log.d("DPVideoScreen", "✅ 内容请求成功: 加载 ${list.size} 条")
                                list.forEachIndexed { index, item ->
                                    val isAd = item["is_ad"] == true || item["isAd"] == true
                                    Log.d("DPVideoScreen", "  [$index] ${if (isAd) "📢 广告" else "🎬 视频"}: $item")
                                }
                            }

                            override fun onDPRequestFail(code: Int, msg: String, map: Map<String, Any>?) {
                                Log.e("DPVideoScreen", "❌ 内容请求失败: code=$code, msg=$msg, detail=$map")
                            }
                        })
                    
                    Log.d("DPVideoScreen", "创建小视频组件，参数: adOffset=3")
                    val widget = DPSdk.factory().createDraw(params)
                    dpWidget = widget
                    
                    widget.fragment?.let { fragment ->
                        Log.d("DPVideoScreen", "添加Fragment到容器")
                        activity.supportFragmentManager.beginTransaction()
                            .replace(id, fragment)
                            .commitAllowingStateLoss()
                    } ?: Log.e("DPVideoScreen", "❌ Fragment为null，创建失败")
                }
            }
        )
    }
}
