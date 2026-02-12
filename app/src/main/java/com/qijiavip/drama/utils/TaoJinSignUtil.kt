package com.qijiavip.drama.utils

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Base64
import android.util.DisplayMetrics
import android.view.WindowManager
import java.security.MessageDigest

object TaoJinSignUtil {
    
    private const val BASE_URL = "https://app.91taojin.com.cn"
    
    fun generateUrl(context: Context, userId: String, taskId: String = ""): String {
        val params = buildParams(context, userId, taskId)
        val sign = generateSign(params)
        params["sign"] = sign
        
        val queryString = params.entries.joinToString("&") { "${it.key}=${it.value}" }
        return "$BASE_URL?$queryString"
    }
    
    private fun buildParams(context: Context, userId: String, taskId: String): LinkedHashMap<String, String> {
        val deviceId = DeviceIdUtil.getDeviceId(context)
        val oaid = DeviceIdUtil.getOaid(context)
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: ""
        val packageName = context.packageName
        val mobileModel = Build.MODEL.replace(" ", "").replace("+", "")
        val sysVer = Build.VERSION.RELEASE
        val serialNumber = try { Build.SERIAL } catch (e: Exception) { "" }
        val screenResolution = getScreenResolution(context)
        val timestamp = System.currentTimeMillis() / 1000
        
        return linkedMapOf(
            "MtId" to Config.TJ_MT_ID,
            "MtIDUser" to userId,
            "IMEI" to deviceId,
            "GetIMEI" to "",
            "OAID" to oaid,
            "Package" to packageName,
            "MobileModel" to mobileModel,
            "SysVer" to sysVer,
            "IMSI" to "",
            "AndroidId" to androidId,
            "SerialNumber" to serialNumber,
            "IDTask" to taskId,
            "ScreenResolution" to screenResolution,
            "Timestamp" to timestamp.toString()
        )
    }
    
    private fun generateSign(params: LinkedHashMap<String, String>): String {
        // 按ASCII排序拼接值
        val sortedKeys = listOf(
            "AndroidId", "GetIMEI", "IDTask", "IMEI", "IMSI", 
            "MobileModel", "MtIDUser", "MtId", "OAID", "Package",
            "ScreenResolution", "SerialNumber", "SysVer", "Timestamp"
        )
        
        val values = sortedKeys.map { params[it] ?: "" }
        val signContent = values.joinToString("#") + "#" + Config.TJ_APP_KEY
        
        // Base64编码(NO_WRAP)
        val base64 = Base64.encodeToString(signContent.toByteArray(), Base64.NO_WRAP)
        
        // MD5加密(32位小写)
        return md5(base64)
    }
    
    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
    
    private fun getScreenResolution(context: Context): String {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getRealMetrics(metrics)
        return "${metrics.widthPixels}x${metrics.heightPixels}"
    }
}
