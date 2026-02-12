# 广告集成使用文档

## 概述

本项目集成了uni-AD原生广告SDK，支持激励视频、插屏、开屏、信息流等多种广告类型。

## 1. SDK集成步骤

### 1.1 复制SDK文件

将以下文件从 `/Users/yiyang/Desktop/projects/UNI_AD_android_5.5.2.0606/SDK/` 复制到项目的 `app/libs/` 目录：

**必需文件：**
- `uniad-native-release.aar` - 核心SDK
- `android-gif-drawable-1.2.29.aar` - GIF支持

**增强广告（按需选择）：**
- `uniad-gdt-release.aar` + `GDTSDK.unionNormal.aar` - 优量汇
- `uniad-ks-release.aar` + `ks_adsdk-ad.aar` - 快手
- `uniad-bd-release.aar` + `Baidu_MobAds_SDK.aar` - 百度
- `uniad-gromore-release.aar` + `open_ad_sdk.aar` - GroMore

### 1.2 添加依赖

在 `app/build.gradle.kts` 中添加：

```kotlin
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    implementation("androidx.recyclerview:recyclerview:1.2.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")
}
```

### 1.3 添加权限

在 `AndroidManifest.xml` 中添加：

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
<uses-permission android:name="android.permission.QUERY_ALL_PACKAGES" />
```

### 1.4 添加混淆规则

在 `proguard-rules.pro` 中添加：

```proguard
# uni-AD基础模块
-keep class io.dcloud.ads.**{*;}
-keep interface io.dcloud.ads.**{*;}
-keep class io.dcloud.openapi.**{*;}
-keep interface io.dcloud.openapi.**{*;}
```

### 1.5 初始化SDK

在 `QijiaApp.kt` 的 `onCreate()` 中添加：

```kotlin
import com.qijiavip.drama.ad.AdManager

class QijiaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // 初始化广告SDK
        AdManager.init(
            context = this,
            appId = "YOUR_APP_ID",  // 替换为实际的应用ID
            adId = "YOUR_AD_ID"     // 替换为实际的联盟ID
        )
        
        // 可选：设置个性化广告
        AdManager.setPersonalAd(this, true)
    }
}
```

## 2. 使用方法

### 2.1 激励视频广告

```kotlin
val rewardAd = AdManager.RewardAd(
    activity = this,
    adpid = "YOUR_REWARD_ADPID",
    userId = "user_123"
)

// 加载广告
rewardAd.load(
    onLoaded = {
        // 加载成功，可以展示
        rewardAd.show(
            onReward = { reward ->
                // 发放奖励
                val points = reward.optInt("points", 0)
                Log.d("Ad", "获得奖励: $points 积分")
            },
            onClose = {
                // 广告关闭
                rewardAd.destroy()
            },
            onError = { code, message ->
                // 播放失败
                Log.e("Ad", "播放失败: $message")
            }
        )
    },
    onError = { code, message ->
        // 加载失败
        Log.e("Ad", "加载失败: $message")
    }
)
```

### 2.2 插屏广告

```kotlin
val interstitialAd = AdManager.InterstitialAd(
    activity = this,
    adpid = "YOUR_INTERSTITIAL_ADPID"
)

interstitialAd.load(
    onLoaded = {
        interstitialAd.show(
            onClose = {
                // 广告关闭
            },
            onError = { code, message ->
                Log.e("Ad", "播放失败: $message")
            }
        )
    },
    onError = { code, message ->
        Log.e("Ad", "加载失败: $message")
    }
)
```

### 2.3 开屏广告

```kotlin
val metrics = resources.displayMetrics
val splashAd = AdManager.SplashAd(
    activity = this,
    adpid = "YOUR_SPLASH_ADPID",
    width = metrics.widthPixels,
    height = (metrics.heightPixels / 5) * 4  // 4/5屏幕高度
)

splashAd.load(
    onLoaded = {
        val container = findViewById<ViewGroup>(R.id.splashContainer)
        splashAd.showIn(
            container = container,
            onClose = {
                // 跳转到主页
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },
            onError = { code, message ->
                // 直接跳转主页
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        )
    },
    onError = { code, message ->
        // 直接跳转主页
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
)
```

### 2.4 信息流广告（Compose）

```kotlin
@Composable
fun NewsListScreen() {
    LazyColumn {
        items(newsList) { news ->
            NewsItem(news)
        }
        
        // 每5条新闻插入一条广告
        item {
            FeedAdView(
                adpid = "YOUR_FEED_ADPID",
                count = 1,
                onAdLoaded = {
                    Log.d("Ad", "信息流广告加载成功")
                },
                onAdError = { code, message ->
                    Log.e("Ad", "信息流广告加载失败: $message")
                }
            )
        }
    }
}
```

## 3. 广告位ID配置

建议在 `Config.kt` 中统一管理广告位ID：

```kotlin
class Config {
    companion object {
        // uni-AD配置
        const val UNIAD_APP_ID = "YOUR_APP_ID"
        const val UNIAD_AD_ID = "YOUR_AD_ID"
        
        // 广告位ID
        const val AD_REWARD_VIDEO = "reward_adpid"      // 激励视频
        const val AD_INTERSTITIAL = "interstitial_adpid" // 插屏
        const val AD_SPLASH = "splash_adpid"            // 开屏
        const val AD_FEED = "feed_adpid"                // 信息流
    }
}
```

## 4. 注意事项

1. **广告位ID获取**：登录 [uni-AD后台](https://uniad.dcloud.net.cn/) 创建应用和广告位
2. **OAID集成**：项目已集成OAID（DeviceIdUtil），无需额外配置
3. **架构支持**：快手和GroMore仅支持 armeabi-v7a 和 arm64-v8a 架构
4. **测试广告**：开发阶段使用测试广告位ID，上线前替换为正式ID
5. **广告预加载**：建议在合适时机提前加载广告，提升展示速度
6. **生命周期管理**：记得在适当时机调用 `destroy()` 释放资源

## 5. 常见问题

### 5.1 广告不展示
- 检查广告位ID是否正确
- 检查网络权限是否授予
- 查看Logcat中的错误日志
- 确认后台广告位已开通并审核通过

### 5.2 填充率低
- 集成更多增强广告SDK（优量汇、快手、百度等）
- 确保OAID正常获取
- 申请必要的权限（定位、存储等）

### 5.3 混淆问题
- 确保添加了所有必要的混淆规则
- 参考 `/Users/yiyang/Desktop/projects/UNI_AD_android_5.5.2.0606/原生广告权限及混淆配置.md`

## 6. 参考文档

- [uni-AD官方文档](https://uniapp.dcloud.io/api/a-d/)
- [原生广告接入文档](file:///Users/yiyang/Desktop/projects/UNI_AD_android_5.5.2.0606/原生广告接入文档.md)
- [权限及混淆配置](file:///Users/yiyang/Desktop/projects/UNI_AD_android_5.5.2.0606/原生广告权限及混淆配置.md)
