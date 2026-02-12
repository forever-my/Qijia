package com.qijiavip.drama.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.github.gzuliyujiang.oaid.DeviceID
import com.github.gzuliyujiang.oaid.DeviceIdentifier
import com.github.gzuliyujiang.oaid.IGetter
import java.util.UUID

/**
 * 设备唯一标识工具类
 * 用于获取设备的各种ID，用于用户识别和防刷机制
 */
object DeviceIdUtil {
    
    private var cachedOaid: String? = null
    private var cachedDeviceId: String? = null
    
    /**
     * 初始化OAID库（建议在Application中调用）
     */
    fun init(context: Context) {
        DeviceID.register(context.applicationContext as Application?)
    }
    
    /**
     * 获取OAID
     * OAID是国内厂商提供的设备标识符，用于替代IMEI
     */
    fun getOaid(context: Context): String {
        if (cachedOaid != null) {
            return cachedOaid!!
        }
        
        val oaid = try {
            DeviceIdentifier.getOAID(context.applicationContext)
        } catch (e: Exception) {
            null
        }
        
        val result = oaid?.takeIf { it.isNotBlank() } ?: getAndroidId(context)
        cachedOaid = result
        return result
    }
    
    /**
     * 获取Android ID
     * 设备首次启动时随机生成，恢复出厂设置后会改变
     */
    @SuppressLint("HardwareIds")
    fun getAndroidId(context: Context): String {
        return try {
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ) ?: generateUUID()
        } catch (e: Exception) {
            generateUUID()
        }
    }
    
    /**
     * 获取设备唯一标识（综合方案）
     * 优先级：OAID > Android ID > UUID
     */
    fun getDeviceId(context: Context): String {
        if (cachedDeviceId != null) {
            return cachedDeviceId!!
        }
        
        val deviceId = getOaid(context)
        cachedDeviceId = deviceId
        return deviceId
    }
    
    /**
     * 获取设备型号
     */
    fun getDeviceModel(): String {
        return "${Build.MANUFACTURER} ${Build.MODEL}"
    }
    
    /**
     * 获取设备品牌
     */
    fun getDeviceBrand(): String {
        return Build.BRAND
    }
    
    /**
     * 获取Android版本
     */
    fun getAndroidVersion(): String {
        return Build.VERSION.RELEASE
    }
    
    /**
     * 获取SDK版本号
     */
    fun getSdkVersion(): Int {
        return Build.VERSION.SDK_INT
    }
    
    /**
     * 生成UUID作为备用方案
     */
    private fun generateUUID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
    
    /**
     * 获取设备指纹（用于防刷）
     * 综合多个设备信息生成唯一指纹
     */
    fun getDeviceFingerprint(context: Context): String {
        val deviceId = getDeviceId(context)
        return buildString {
            append(deviceId)
            append("|")
            append(getDeviceModel())
            append("|")
            append(getAndroidVersion())
            append("|")
            append(getSdkVersion())
        }.hashCode().toString()
    }
    }
    

