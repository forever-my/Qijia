# 悬赏蛙任务对接说明

## 配置步骤

### 1. 获取媒体ID和KEY

1. 访问悬赏蛙媒体主后台：https://union.xuanshangwa.com.cn/
2. 登录后获取媒体ID（mt_id）和媒体KEY（mt_key）
3. 在 `XuanShangWaSignUtil.kt` 中替换以下常量：

```kotlin
private const val MT_ID = "YOUR_MT_ID"     // 替换为实际的媒体ID
private const val MT_KEY = "YOUR_MT_KEY"   // 替换为实际的媒体KEY
```

### 2. 配置用户ID

在 `MainScreen.kt` 中的福利中心点击事件中，替换用户ID：

```kotlin
userId = "user_${System.currentTimeMillis()}", // TODO: 替换为实际用户ID
```

应该使用实际的用户ID，例如：

```kotlin
userId = currentUser.id.toString(),
```

## 功能说明

### 签名机制

签名规则：`md5(base64_encode('device_id#imei1#imei2#mobile_model#mt_id#mt_user_id#oaid#sys_ver#timestamp#mt_key'))`

参数按字母顺序拼接，使用 `#` 作为分隔符。

### 设备标识获取

- **Android 10以下**：优先使用 device_id、imei1、imei2
- **Android 10及以上**：使用 OAID（通过 DeviceIdUtil 获取）

### URL生成

工具类会自动生成完整的任务URL，包含所有必需参数和签名。

## 使用示例

```kotlin
// 在需要跳转到悬赏蛙任务的地方
DeviceIdUtil.getDeviceId(context) { deviceId ->
    val url = XuanShangWaSignUtil.generateTaskUrl(
        userId = currentUser.id.toString(),
        deviceId = deviceId,
        imei1 = "",
        imei2 = "",
        oaid = deviceId,
        mobileModel = XuanShangWaSignUtil.getProcessedDeviceModel(),
        sysVer = XuanShangWaSignUtil.getSystemVersion()
    )
    // 跳转到WebView
    navController.navigate(Routes.webview(url, "福利中心"))
}
```

## 特定页面直达

如需直达特定页面，可修改 `XuanShangWaSignUtil.kt` 中的 URL：

```kotlin
// 直达"我的任务"页
val url = "https://appunion.xuanshangwa.com.cn/task_union_1?$params"

// 直达"任务详情页"
val url = "https://appunion.xuanshangwa.com.cn/task_union_detail?task_id=任务ID&$params"
```

## 订单回调

需要在悬赏蛙后台配置回调地址，用于接收用户完成任务的通知。

详细对接方法请参考悬赏蛙文档：https://doc.xuanshangwa.com.cn/union

## 注意事项

1. **设备型号处理**：自动去除空格和+号，避免签名校验失败
2. **Base64编码**：使用 `Base64.NO_WRAP` 标志，确保编码结果一致
3. **参数值为空**：空值使用空字符串参与签名
4. **时间戳**：使用秒级时间戳
5. **OAID获取**：已通过 DeviceIdUtil 自动处理，支持主流厂商

## 测试

可以在日志中查看生成的URL和签名信息：

```
D/XuanShangWaSign: Sign content: ...
D/XuanShangWaSign: Base64: ...
D/XuanShangWaSign: Sign: ...
D/XuanShangWaSign: Generated URL: ...
```

## 常见问题

### Q: 签名校验失败？
A: 检查以下几点：
- MT_ID 和 MT_KEY 是否正确
- 设备型号是否正确处理（去除空格和+号）
- Base64编码是否使用 NO_WRAP 标志
- 参数顺序是否正确（按字母顺序）

### Q: OAID获取失败？
A: DeviceIdUtil 会自动降级到 Android ID，不影响使用

### Q: 如何支持不同等级用户不同奖励？
A: 使用 `mtShareLevel` 参数，需先与业务经理沟通确认可用档位
