# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ==================== OAID 混淆规则 ====================
# 保持OAID库的核心类不被混淆
-keep class com.github.gzuliyujiang.oaid.** { *; }
-dontwarn com.github.gzuliyujiang.oaid.**

# 保持各厂商的OAID实现类
-keep class XI.CA.XI.** { *; }
-keep class XI.K0.XI.** { *; }
-keep class XI.XI.K0.** { *; }
-keep class XI.vs.K0.** { *; }
-keep class XI.xo.XI.XI.** { *; }
-keep class com.asus.msa.SupplementaryDID.** { *; }
-keep class com.asus.msa.sdid.** { *; }
-keep class com.bun.lib.** { *; }
-keep class com.bun.miitmdid.** { *; }
-keep class com.huawei.hms.ads.identifier.** { *; }
-keep class com.samsung.android.deviceidservice.** { *; }
-keep class com.zui.opendeviceidlibrary.** { *; }
-keep class org.json.** { *; }

# 保持设备ID工具类
-keep class com.qijiavip.drama.utils.DeviceIdUtil { *; }

# ==================== Retrofit & OkHttp ====================
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-dontwarn retrofit2.**
-dontwarn okhttp3.**

# ==================== Gson ====================
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# ==================== Compose ====================
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# ==================== Hilt ====================
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-dontwarn dagger.hilt.**

# ==================== 快手SDK ====================
-keep class com.kwad.** { *; }
-dontwarn com.kwad.**