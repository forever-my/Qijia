package com.qijiavip.drama.utils

import android.content.Context
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory

object WeChatLoginUtil {
    
    private const val APP_ID = Config.WECHAT_APP_ID
    private var wxApi: IWXAPI? = null
    
    /**
     * 初始化微信SDK
     * 在Application.onCreate()中调用
     */
    fun init(context: Context) {
        wxApi = WXAPIFactory.createWXAPI(context, APP_ID, true)
        wxApi?.registerApp(APP_ID)
    }
    
    /**
     * 检查微信是否已安装
     */
    fun isWeChatInstalled(): Boolean {
        return wxApi?.isWXAppInstalled == true
    }
    
    /**
     * 发起微信登录请求
     */
    fun login() {
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "wechat_login_${System.currentTimeMillis()}"
        wxApi?.sendReq(req)
    }
    
    /**
     * 获取WXAPI实例
     */
    fun getWXAPI(): IWXAPI? = wxApi
}
