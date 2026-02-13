package com.qijiavip.drama.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.dcloud.ads.*
import io.dcloud.ads.core.DCloudAdManager
import io.dcloud.ads.core.entry.DCloudAdSlot
import io.dcloud.ads.core.entry.SplashConfig
import io.dcloud.ads.core.v2.feed.DCFeedAd
import io.dcloud.ads.core.v2.feed.DCFeedAdListener
import io.dcloud.ads.core.v2.feed.DCFeedAdLoadListener
import io.dcloud.ads.core.v2.feed.DCFeedAdLoader
import io.dcloud.ads.core.v2.interstitial.DCInterstitialAd
import io.dcloud.ads.core.v2.interstitial.DCInterstitialAdListener
import io.dcloud.ads.core.v2.interstitial.DCInterstitialAdLoadListener
import io.dcloud.ads.core.v2.reward.DCRewardAd
import io.dcloud.ads.core.v2.reward.DCRewardAdListener
import io.dcloud.ads.core.v2.reward.DCRewardAdLoadListener
import io.dcloud.ads.core.v2.splash.DCSplashAd
import io.dcloud.ads.core.v2.splash.DCSplashAdListener
import io.dcloud.ads.core.v2.splash.DCSplashAdLoadListener
import org.json.JSONArray
import org.json.JSONObject

/**
 * 广告管理工具类
 * 封装uni-AD SDK的各种广告类型
 */
object AdManager {
    private const val TAG = "AdManager"
    
    /**
     * 初始化广告SDK
     * 在Application.onCreate()中调用
     */
    fun init(context: Context, appId: String, adId: String) {
        val config = DCloudAdManager.InitConfig()
            .setAppId(appId)
            .setAdId(adId)
        DCloudAdManager.init(context, config)
        Log.d(TAG, "广告SDK初始化完成")
    }
    
    /**
     * 设置个性化广告开关
     */
    fun setPersonalAd(context: Context, isOpen: Boolean) {
        DCloudAdManager.setPersonalAd(context, isOpen)
    }
    
    /**
     * 激励视频广告
     */
    class RewardAd(
        private val activity: Activity,
        private val adpid: String,
        private val userId: String? = null
    ) {
        private var rewardAd: DCRewardAd? = null
        private var onRewardCallback: (JSONObject) -> Unit = {}
        private var onCloseCallback: () -> Unit = {}
        private var onErrorCallback: (Int, String) -> Unit = { _, _ -> }
        
        fun load(
            onLoaded: () -> Unit = {},
            onError: (Int, String) -> Unit = { _, _ -> }
        ) {
            if (rewardAd == null) {
                rewardAd = DCRewardAd(activity)
            }
            
            // 先设置监听器
            rewardAd?.setRewardAdListener(object : DCRewardAdListener {
                override fun onReward(reward: JSONObject) {
                    Log.d(TAG, "激励视频奖励: $reward")
                    onRewardCallback(reward)
                }
                override fun onShow() {}
                override fun onClick() {}
                override fun onVideoPlayEnd() {}
                override fun onSkip() {}
                override fun onClose() {
                    Log.d(TAG, "激励视频关闭")
                    onCloseCallback()
                }
                override fun onShowError(code: Int, message: String) {
                    Log.e(TAG, "激励视频播放失败: $code - $message")
                    onErrorCallback(code, message)
                }
            })
            
            // 再加载广告
            val slot = DCloudAdSlot.Builder()
                .adpid(adpid)
                .userId("user_"+System.currentTimeMillis())
                .build()
                
            rewardAd?.load(slot, object : DCRewardAdLoadListener {
                override fun onRewardAdLoad() {
                    Log.d(TAG, "激励视频加载成功")
                    onLoaded()
                }
                
                override fun onError(code: Int, message: String, detail: JSONArray?) {
                    Log.e(TAG, "激励视频加载失败: $code - $message")
                    onError(code, message)
                }
            })
        }
        
        fun show(
            onReward: (JSONObject) -> Unit = {},
            onClose: () -> Unit = {},
            onError: (Int, String) -> Unit = { _, _ -> }
        ) {
            onRewardCallback = onReward
            onCloseCallback = onClose
            onErrorCallback = onError
            
            rewardAd?.show(activity)
        }

        fun destroy() {
            rewardAd?.destroy()
            rewardAd = null
        }
    }
    
    /**
     * 插屏广告
     */
    class InterstitialAd(
        private val activity: Activity,
        private val adpid: String
    ) {
        private var interstitialAd: DCInterstitialAd? = null
        
        fun load(
            onLoaded: () -> Unit = {},
            onError: (Int, String) -> Unit = { _, _ -> }
        ) {
            interstitialAd = DCInterstitialAd(activity).apply {
                val slot = DCloudAdSlot.Builder().adpid(adpid).build()
                load(slot, object : DCInterstitialAdLoadListener {
                    override fun onInterstitialAdLoad() {
                        Log.d(TAG, "插屏广告加载成功")
                        onLoaded()
                    }
                    
                    override fun onError(code: Int, message: String, detail: JSONArray?) {
                        Log.e(TAG, "插屏广告加载失败: $code - $message")
                        onError(code, message)
                    }
                })
            }
        }
        
        fun show(
            onClose: () -> Unit = {},
            onError: (Int, String) -> Unit = { _, _ -> }
        ) {
            interstitialAd?.apply {
                if (isValid) {
                    setInterstitialAdListener(object : DCInterstitialAdListener {
                        override fun onShow() {}
                        override fun onClick() {}
                        override fun onVideoPlayEnd() {}
                        override fun onSkip() {}
                        override fun onClose() {
                            Log.d(TAG, "插屏广告关闭")
                            onClose()
                        }
                        override fun onShowError(code: Int, message: String) {
                            Log.e(TAG, "插屏广告播放失败: $code - $message")
                            onError(code, message)
                        }
                    })
                    show(activity)
                }
            }
        }
    }
    
    /**
     * 开屏广告
     */
    class SplashAd(
        private val activity: Activity,
        private val adpid: String,
        private val width: Int,
        private val height: Int
    ) {
        private var splashAd: DCSplashAd? = null
        
        fun load(
            onLoaded: () -> Unit = {},
            onError: (Int, String) -> Unit = { _, _ -> }
        ) {
            splashAd = DCSplashAd(activity).apply {
                val config = SplashConfig.Builder()
                    .width(width)
                    .height(height)
                    .build()
                    
                load(config, object : DCSplashAdLoadListener {
                    override fun onSplashAdLoad() {
                        Log.d(TAG, "开屏广告加载成功")
                        onLoaded()
                    }

                    override fun redBag(
                        p0: View?,
                        p1: FrameLayout.LayoutParams?
                    ) {
                        TODO("Not yet implemented")
                    }

                    override fun onError(code: Int, message: String, detail: JSONArray?) {
                        Log.e(TAG, "开屏广告加载失败: $code - $message")
                        onError(code, message)
                    }
                })
            }
        }
        
        fun showIn(
            container: ViewGroup,
            onClose: () -> Unit = {},
            onError: (Int, String) -> Unit = { _, _ -> }
        ) {
            splashAd?.apply {
                setSplashAdListener(object : DCSplashAdListener {
                    override fun onShow() {}
                    override fun onClick() {}
                    override fun onVideoPlayEnd() { onClose() }
                    override fun onSkip() { onClose() }
                    override fun onClose() {
                        Log.d(TAG, "开屏广告关闭")
                        onClose()
                    }
                    override fun onShowError(code: Int, message: String) {
                        Log.e(TAG, "开屏广告播放失败: $code - $message")
                        onError(code, message)
                    }
                })
                showIn(container)
            }
        }
    }
    /**
     * 加载信息流广告（返回广告对象列表）
     */
    fun loadFeedAds(
        activity: Activity,
        adpid: String,
        count: Int,
        onLoaded: (List<DCFeedAd>) -> Unit,
        onError: (Int, String) -> Unit = { _, _ -> }
    ) {
        val loader = DCFeedAdLoader(activity)
        val slot = DCloudAdSlot.Builder()
            .adpid(adpid)
            .count(count)
            .build()

        loader.load(slot, object : DCFeedAdLoadListener {
            override fun onFeedAdLoad(ads: List<DCFeedAd>) {
                Log.d("AdManager", "信息流广告加载成功: ${ads.size}条")
                onLoaded(ads)
            }

            override fun onError(code: Int, message: String, detail: JSONArray?) {
                Log.e("AdManager", "信息流广告加载失败: $code - $message")
                onError(code, message)
            }
        })
    }
    
    /**
     * 渲染单条信息流广告
     */
    @Composable
    fun SingleFeedAdView(feedAd: DCFeedAd) {
        val context = LocalContext.current
        val activity = context as? Activity ?: return
        
        AndroidView(
            factory = { ctx ->
                val container = android.widget.FrameLayout(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
                
                feedAd.setFeedAdListener(object : DCFeedAdListener {
                    override fun onRenderSuccess() {
                        Log.d("AdManager", "信息流广告渲染成功")
                        val adView = feedAd.getFeedAdView(activity)
                        if (adView != null) {
                            (adView.parent as? ViewGroup)?.removeView(adView)
                            container.removeAllViews()
                            container.addView(adView, ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            ))
                        }
                    }
                    override fun onRenderFail() {
                        Log.e("AdManager", "信息流广告渲染失败")
                    }
                    override fun onShow() {}
                    override fun onClosed(p0: String?) {}
                    override fun onClick() {}
                    override fun onShowError() {}
                })
                feedAd.render()
                
                container
            }
        )
    }
    
    /**
     * Compose信息流广告组件
     */
    @Composable
    fun FeedAdView(
        adpid: String,
        count: Int = 1,
        onAdLoaded: () -> Unit = {},
        onAdError: (Int, String) -> Unit = { _, _ -> }
    ) {
        val context = LocalContext.current
        val activity = context as? Activity ?: return

        var adContainer by remember { mutableStateOf<android.widget.LinearLayout?>(null) }

        DisposableEffect(adpid) {
            val loader = DCFeedAdLoader(activity)
            val slot = DCloudAdSlot.Builder()
                .adpid(adpid)
                .count(count)
                .build()

            loader.load(slot, object : DCFeedAdLoadListener {
                override fun onFeedAdLoad(ads: List<DCFeedAd>) {
                    Log.d("AdManager", "信息流广告加载成功: ${ads.size}条")
                    ads.forEach { ad ->
                        ad.setFeedAdListener(object : DCFeedAdListener {
                            override fun onRenderSuccess() {
                                Log.d("AdManager", "信息流广告渲染成功")
                                ad.getFeedAdView(activity)?.let { view ->
                                    adContainer?.addView(view)
                                }
                            }
                            override fun onRenderFail() {
                                Log.e("AdManager", "信息流广告渲染失败")
                            }
                            override fun onShow() {
                                Log.d("AdManager", "信息流广告展示成功")
                            }
                            override fun onClosed(p0: String?) {}
                            override fun onClick(
                            ) {
                                Log.d("AdManager", "信息流广告点击")
                            }
                            override fun onShowError() {
                                Log.e("AdManager", "信息流广告展示失败")
                            }
                        })
                        ad.render()
                    }
                    onAdLoaded()
                }

                override fun onError(code: Int, message: String, detail: JSONArray?) {
                    Log.e("AdManager", "信息流广告加载失败: $code - $message")
                    onAdError(code, message)
                }
            })

            onDispose {
                adContainer?.removeAllViews()
            }
        }

        AndroidView(
            factory = { ctx ->
                android.widget.LinearLayout(ctx).apply {
                    orientation = android.widget.LinearLayout.VERTICAL
                    layoutParams = android.view.ViewGroup.LayoutParams(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    adContainer = this
                }
            }
        )
    }
}


