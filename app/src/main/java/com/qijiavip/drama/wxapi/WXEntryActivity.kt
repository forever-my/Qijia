package com.qijiavip.drama.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.qijiavip.drama.utils.WeChatLoginUtil
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

/**
 * 微信登录回调Activity
 * 必须放在包名.wxapi包下，且类名必须为WXEntryActivity
 */
class WXEntryActivity : Activity(), IWXAPIEventHandler {
    
    companion object {
        const val TAG = "WXEntryActivity"
        var onLoginResult: ((code: String?, errCode: Int) -> Unit)? = null
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WeChatLoginUtil.getWXAPI()?.handleIntent(intent, this)
    }
    
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        WeChatLoginUtil.getWXAPI()?.handleIntent(intent, this)
    }
    
    override fun onReq(req: BaseReq?) {
        Log.d(TAG, "onReq: ${req?.type}")
    }
    
    override fun onResp(resp: BaseResp?) {
        Log.d(TAG, "onResp: errCode=${resp?.errCode}")
        
        when (resp?.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                if (resp is SendAuth.Resp) {
                    val code = resp.code
                    Log.d(TAG, "微信登录成功: code=$code")
                    onLoginResult?.invoke(code, resp.errCode)
                }
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
                Log.d(TAG, "用户取消授权")
                onLoginResult?.invoke(null, resp.errCode)
            }
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
                Log.d(TAG, "用户拒绝授权")
                onLoginResult?.invoke(null, resp.errCode)
            }
            else -> {
                Log.e(TAG, "微信登录失败: ${resp?.errCode}")
                onLoginResult?.invoke(null, resp?.errCode ?: -1)
            }
        }
        
        finish()
    }
}
