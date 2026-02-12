# 设备ID工具类使用说明

## 功能说明

`DeviceIdUtil` 提供了获取设备唯一标识的多种方法，用于用户识别和防刷机制。

## 初始化

已在 `QijiaApplication` 中自动初始化，无需手动调用。

```kotlin
// 在Application的onCreate中
DeviceIdUtil.init(this)
```

## 使用方法

### 1. 获取OAID（推荐）

OAID是国内厂商提供的设备标识符，用于替代IMEI，支持华为、小米、OPPO、VIVO等主流厂商。

```kotlin
DeviceIdUtil.getOaid(context) { oaid ->
    Log.d("DeviceId", "OAID: $oaid")
    // 使用oaid进行用户识别或上报
}
```

### 2. 获取Android ID

设备首次启动时随机生成，恢复出厂设置后会改变。

```kotlin
val androidId = DeviceIdUtil.getAndroidId(context)
Log.d("DeviceId", "Android ID: $androidId")
```

### 3. 获取设备唯一标识（综合方案）

优先级：OAID > Android ID > UUID

```kotlin
DeviceIdUtil.getDeviceId(context) { deviceId ->
    Log.d("DeviceId", "Device ID: $deviceId")
    // 用于API请求的设备标识
}
```

### 4. 获取设备指纹（防刷）

综合多个设备信息生成唯一指纹，用于防刷机制。

```kotlin
DeviceIdUtil.getDeviceFingerprint(context) { fingerprint ->
    Log.d("DeviceId", "Fingerprint: $fingerprint")
    // 用于防刷检测
}
```

### 5. 获取设备信息

```kotlin
val model = DeviceIdUtil.getDeviceModel()        // 例如: "Xiaomi MI 11"
val brand = DeviceIdUtil.getDeviceBrand()        // 例如: "Xiaomi"
val version = DeviceIdUtil.getAndroidVersion()   // 例如: "12"
val sdk = DeviceIdUtil.getSdkVersion()           // 例如: 31
```

## 实际应用场景

### 场景1：用户登录/注册

```kotlin
DeviceIdUtil.getDeviceId(context) { deviceId ->
    val loginRequest = LoginRequest(
        phone = phone,
        deviceId = deviceId,
        deviceModel = DeviceIdUtil.getDeviceModel(),
        osVersion = DeviceIdUtil.getAndroidVersion()
    )
    // 发送登录请求
}
```

### 场景2：防刷检测

```kotlin
DeviceIdUtil.getDeviceFingerprint(context) { fingerprint ->
    val taskRequest = TaskRequest(
        userId = userId,
        taskId = taskId,
        deviceFingerprint = fingerprint
    )
    // 提交任务完成请求，后台可根据fingerprint检测异常行为
}
```

### 场景3：数据上报

```kotlin
DeviceIdUtil.getDeviceId(context) { deviceId ->
    val analyticsData = AnalyticsData(
        deviceId = deviceId,
        deviceModel = DeviceIdUtil.getDeviceModel(),
        deviceBrand = DeviceIdUtil.getDeviceBrand(),
        osVersion = DeviceIdUtil.getAndroidVersion(),
        sdkVersion = DeviceIdUtil.getSdkVersion()
    )
    // 上报统计数据
}
```

## 注意事项

1. **异步调用**：`getOaid()`、`getDeviceId()`、`getDeviceFingerprint()` 是异步方法，需要通过回调获取结果。

2. **缓存机制**：工具类内部会缓存OAID和DeviceId，避免重复获取。

3. **权限要求**：
   - 不需要额外的运行时权限
   - AndroidManifest.xml中已配置必要的权限

4. **混淆配置**：
   - 已在 `proguard-rules.pro` 中配置混淆规则
   - 不要删除或修改OAID相关的混淆规则

5. **厂商支持**：
   - 支持华为、小米、OPPO、VIVO、魅族、联想、三星、华硕等主流厂商
   - 不支持的设备会自动降级到Android ID

6. **隐私合规**：
   - 使用设备标识前需要在隐私政策中声明
   - 需要用户同意后才能获取和使用

## 测试

```kotlin
// 清除缓存（仅用于测试）
DeviceIdUtil.clearCache()
```

## 依赖版本

- OAID库版本：4.2.9
- 仓库：JitPack (https://jitpack.io)
