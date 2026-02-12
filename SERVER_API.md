# 服务端接口文档
## 概述
- 接口地址 http://211.101.244.250:10802/swagger-ui/index.html
- 自动化构建看板的地址 http://211.101.244.250:10801/qijia/qijia2026!
- 代码仓库 http://211.101.244.250:10801/qijia/qijia2026!
- 日志看板
  地址：http://211.101.244.250:3000/
  账号密码：admin/qijiagra2026!
本文档描述了积分任务资讯应用的服务端API接口规范。

## 目录

- [接口规范](#接口规范)
- [认证机制](#认证机制)
- [用户相关](#用户相关)
- [资讯相关](#资讯相关)
- [任务相关](#任务相关)
- [积分相关](#积分相关)
- [提现相关](#提现相关)
- [广告相关](#广告相关)
- [错误码](#错误码)

## 接口规范

### 基础信息

- 基础URL: `https://api.example.com/v1`
- 请求方式: `GET` / `POST`
- 数据格式: `JSON`
- 字符编码: `UTF-8`

### 通用请求头

```
Content-Type: application/json
Authorization: Bearer {token}
Device-Id: {device_id}
App-Version: {version}
```

### 通用响应格式

```json
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

## 认证机制

## 用户相关

## 资讯相关

## 任务相关

## 积分相关

## 提现相关

## 广告相关

## 错误码
