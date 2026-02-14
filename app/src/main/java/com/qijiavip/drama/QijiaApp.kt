package com.qijiavip.drama

import android.app.Application
import android.util.Log
import com.bytedance.sdk.dp.DPSdk
import com.bytedance.sdk.dp.DPSdkConfig
import com.bytedance.sdk.openadsdk.TTAdConfig
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.qijiavip.drama.utils.Config
import com.qijiavip.drama.utils.DeviceIdUtil
import com.qijiavip.drama.utils.WeChatLoginUtil
import dagger.hilt.android.HiltAndroidApp
import io.dcloud.ads.core.DCloudAdManager

@HiltAndroidApp
class QijiaApp : Application() {
    
    companion object {
        var isDPStarted = false
    }
    
    override fun onCreate() {
        super.onCreate()
        initDeviceId()
        initUniAd() // 初始化uni-AD
        initCsjAd() // 初始化穿山甲广告SDK
        WeChatLoginUtil.init(this)
    }
    
    private fun initUniAd() {
        val config = DCloudAdManager.InitConfig()
        config.setAdId(Config.UNI_ADID).setAppId(Config.UNI_APPID)
        
        DCloudAdManager.init(this, config)
        
        // 初始化之后立即调用setPrivacyConfig方法。需要在请求广告之前调用，否则不会生效
        DCloudAdManager.setPrivacyConfig(object : DCloudAdManager.PrivacyConfig() {
            override fun isAdult(): Boolean = true
            override fun isCanUsePhoneState(): Boolean = true
            override fun isCanUseStorage(): Boolean = true
            override fun isCanUseLocation(): Boolean = true
            override fun isCanUseWifiState(): Boolean = true
            override fun isCanGetInstallAppList(): Boolean = true
            override fun isCanGetRunningApps(): Boolean = true
            override fun isCanGetMacAddress(): Boolean = true
            override fun isCanGetAndroidId(): Boolean = true
            override fun isCanGetOAID(): Boolean = true
            override fun isCanGetIP(): Boolean = true
            override fun isCanUseSensor(): Boolean = true
            override fun isCanUseSimOperator(): Boolean = true
            override fun isCanUseRecordPermission(): Boolean = true
        })
    }
    
    private fun initCsjAd() {
        // 1. 先初始化广告SDK
        val config = TTAdConfig.Builder()
            .appId(Config.CSJ_APP_ID)
            .appName(getString(R.string.app_name))
            .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
            .allowShowNotify(true)
            .supportMultiProcess(false)
            .debug(true)
            .build()
        
        Log.d("QijiaApp", "开始初始化穿山甲广告SDK, AppID: ${Config.CSJ_APP_ID}")
        TTAdSdk.init(this, config)
        
        // 2. 启动广告SDK
        TTAdSdk.start(object : TTAdSdk.Callback {
            override fun success() {
                Log.d("QijiaApp", "穿山甲广告SDK启动成功")
                Log.d("QijiaApp", "TTAdSdk.isInitSuccess: ${TTAdSdk.isInitSuccess()}")
                // 3. 广告SDK启动成功后，初始化内容SDK
                initDPSdk()
            }
            
            override fun fail(code: Int, msg: String?) {
                Log.e("QijiaApp", "穿山甲广告SDK启动失败: code=$code, msg=$msg")
            }
        })
    }
    
    private fun initDeviceId() {
        DeviceIdUtil.init(this)
    }
    
    private fun initDPSdk() {
        Log.d("QijiaApp", "开始初始化穿山甲内容SDK")
        // 穿山甲小视频SDK初始化（需要先从平台下载配置JSON文件放到assets目录）
        val config = DPSdkConfig.Builder()
            .debug(true)
            .build()
        
        // 注意：需要将从穿山甲平台下载的配置JSON文件放到 app/src/main/assets/ 目录
        // 文件名格式：SDK_Setting_xxxxx.json
        DPSdk.init(this, "SDK_Setting.json", config)
        
        // 启动SDK服务
        DPSdk.start{isSuccess, message ->
            //请确保使用Sdk时Sdk已经成功启动
            //isSuccess=true表示启动成功
            //启动失败，可以再次调用启动接口（建议最多不要超过3次)
            isDPStarted = isSuccess
            Log.d("QijiaApp", "内容SDK启动结果: isSuccess=$isSuccess, msg=$message")
            Log.d("QijiaApp", "DPSdk.isStartSuccess: ${DPSdk.isStartSuccess()}")
        }
    }
}
