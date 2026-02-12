package com.qijiavip.drama

import android.app.Application
import android.util.Log
import com.kwad.sdk.api.KsAdSDK
import com.kwad.sdk.api.KsInitCallback
import com.kwad.sdk.api.SdkConfig
import com.qijiavip.drama.ad.AdManager
import com.qijiavip.drama.utils.Config
import com.qijiavip.drama.utils.DeviceIdUtil
import com.qijiavip.drama.utils.WeChatLoginUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QijiaApp : Application() {
    
    companion object {
        var isSdkStarted = false
    }
    
    override fun onCreate() {
        super.onCreate()
        initDeviceId()
        initKuaishouSDK()
        AdManager.init(this, Config.UNI_APPID, Config.UNI_ADID)
        WeChatLoginUtil.init(this)
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
