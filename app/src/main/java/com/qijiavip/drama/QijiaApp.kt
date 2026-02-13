package com.qijiavip.drama

import android.app.Application
import android.util.Log
import com.kwad.sdk.api.KsAdSDK
import com.kwad.sdk.api.KsInitCallback
import com.kwad.sdk.api.SdkConfig
import com.qijiavip.drama.utils.Config
import com.qijiavip.drama.utils.DeviceIdUtil
import com.qijiavip.drama.utils.WeChatLoginUtil
import dagger.hilt.android.HiltAndroidApp
import io.dcloud.ads.core.DCloudAdManager

@HiltAndroidApp
class QijiaApp : Application() {
    
    companion object {
        var isSdkStarted = false
    }
    
    override fun onCreate() {
        super.onCreate()
        initDeviceId()
        initKuaishouSDK()
        initUniAd()
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
        
        Log.d("QijiaApp", "uni-AD初始化完成")
    }
    
    private fun initDeviceId() {
        DeviceIdUtil.init(this)
    }
    
    private fun initKuaishouSDK() {
        val startTime = System.currentTimeMillis()
        
        KsAdSDK.init(this, SdkConfig.Builder()
            .appId(Config.KS_APPID)
            .appName("齐加")
            .showNotification(true)
            .debug(true)
            .setInitCallback(object : KsInitCallback {
                override fun onSuccess() {
                    Log.i("KsAdSDK", "init success time: ${System.currentTimeMillis() - startTime}")
                    KsAdSDK.start()
                }
                
                override fun onFail(code: Int, msg: String?) {
                    Log.e("KsAdSDK", "init fail code:$code msg:$msg")
                }
            })
            .setStartCallback(object : KsInitCallback {
                override fun onSuccess() {
                    Log.i("KsAdSDK", "start success")
                    isSdkStarted = true
                }
                
                override fun onFail(code: Int, msg: String?) {
                    Log.e("KsAdSDK", "start fail msg:$msg")
                }
            })
            .build())
    }
}
