package com.qijiavip.drama.utils

import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.paging.Config
import java.security.MessageDigest
import java.util.SortedMap

/**
 * 悬赏蛙任务签名工具类
 */
object XuanShangWaSignUtil {
    
    private const val TAG = "XuanShangWaSign"
    
    // 媒体ID和KEY需要从悬赏蛙后台获取
    private const val MT_ID = Config.XS_APP_ID
    private const val MT_KEY = Config.XS_APP_KEY
    
    /**
     * 生成悬赏蛙任务URL
     * @param userId 媒体APP用户ID
     * @param deviceId 设备ID
     * @param imei1 IMEI1
     * @param imei2 IMEI2
     * @param oaid OAID
     * @param mobileModel 设备型号
     * @param sysVer 系统版本
     * @param mtShareLevel 媒体分佣比例档位（可选）
     */
    fun generateTaskUrl(
        userId: String,
        deviceId: String,
        imei1: String,
        imei2: String,
        oaid: String,
        mobileModel: String,
        sysVer: String,
        mtShareLevel: Int? = null
    ): String {
        val timestamp = System.currentTimeMillis() / 1000
        
        // 处理设备型号：去除空格和+号
        val processedModel = mobileModel.replace(" ", "").replace("+", "")
        
        // 构建参数Map（按ASCII码自动排序）
        val params = sortedMapOf<String, String>()
        params["device_id"] = deviceId
        params["imei1"] = imei1
        params["imei2"] = imei2
        params["mobile_model"] = processedModel
        params["mt_id"] = MT_ID
        params["mt_user_id"] = userId
        params["oaid"] = oaid
        params["sys_ver"] = sysVer
        params["timestamp"] = timestamp.toString()
        mtShareLevel?.let { params["mt_share_level"] = it.toString() }
        
        // 生成签名
        val sign = generateSign(params, MT_KEY)
        
        // 构建URL参数
        val urlParams = buildString {
            params.forEach { (key, value) ->
                if (isNotEmpty()) append("&")
                append("$key=$value")
            }
            append("&sign=$sign")
        }
        
        val url = "https://appunion.xuanshangwa.com.cn/task?$urlParams"
        Log.d(TAG, "Generated URL: $url")
        return url
    }
    
    /**
     * 生成签名
     * 规则：
     * 1. 参数按ASCII码排序
     * 2. 使用#拼接所有value值
     * 3. 最后拼接#mt_key
     * 4. Base64编码（NO_WRAP）
     * 5. MD5加密（32位小写）
     */
    private fun generateSign(params: SortedMap<String, String>, mtKey: String): String {
        // 使用#拼接所有value值
        val signContent = buildString {
            params.values.forEach { value ->
                if (isNotEmpty()) append("#")
                append(value)
            }
            append("#")
            append(mtKey)
        }
        
        Log.d(TAG, "Sign content: $signContent")
        
        // Base64编码（NO_WRAP模式）
        val base64Bytes = Base64.encode(signContent.toByteArray(), Base64.NO_WRAP)
        val base64String = String(base64Bytes)
        
        Log.d(TAG, "Base64: $base64String")
        
        // MD5加密（32位小写）
        val md5 = md5(base64String)
        
        Log.d(TAG, "Sign: $md5")
        
        return md5
    }
    
    /**
     * MD5加密
     */
    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
    
    /**
     * 获取设备型号（已处理空格和+号）
     */
    fun getProcessedDeviceModel(): String {
        return Build.MODEL.replace(" ", "").replace("+", "")
    }
    
    /**
     * 获取系统版本
     */
    fun getSystemVersion(): String {
        return Build.VERSION.RELEASE
    }
}
