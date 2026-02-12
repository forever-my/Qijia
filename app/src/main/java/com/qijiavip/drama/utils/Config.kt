package com.qijiavip.drama.utils

class Config {
    companion object {
        // 悬赏蛙配置
        const val XS_APP_ID = "2038"// TODO: 替换为实际的媒体ID 悬赏蛙媒体ID
        const val XS_APP_KEY = "1a1dc063e8f8d6d75f2d91ded1a870e2"// TODO: 替换为实际的媒体KEY 悬赏蛙媒体密钥

        //uni-AD
        const val UNI_APPID = "__UNI__19C976D"
        const val UNI_ADID = "120355050801"
        const val UNI_ADPID="1115660607"

        const val KS_APP_USER_NAME = "your_app_user_name"
        const val KS_APP_USER_AVATAR = "your_app_user_avatar"
        const val KS_APP_USER_GENDER = "your_app_user_gender"
        const val KS_APP_USER_BIRTHDAY = "your_app_user_birthday"
        // 快手聚合内容配置
        const val KS_APPID = "90009"
        const val KS_POSID = 90009005L
        // 91淘金配置
        const val TJ_MT_ID = "4712" // TODO: 替换为实际的媒体ID
        const val TJ_APP_KEY = "857b46c653b5b1e1e9ccad8906d927fd" // TODO: 替换为实际的AppKey
        
        // 微信开放平台配置
        const val WECHAT_APP_ID = "wxc74b99629c5f2dc1" // TODO: 替换为实际的微信AppID
        const val WECHAT_APP_SECRET = "YOUR_WECHAT_APP_SECRET" // TODO: 替换为实际的微信AppSecret
        
        // uni-AD广告位ID
        const val AD_REWARD_VIDEO = "reward_adpid" // TODO: 替换为实际的激励视频广告位ID
        const val AD_INTERSTITIAL = "interstitial_adpid" // TODO: 替换为实际的插屏广告位ID
        const val AD_SPLASH = "splash_adpid" // TODO: 替换为实际的开屏广告位ID
        const val AD_FEED = "feed_adpid" // TODO: 替换为实际的信息流广告位ID
    }
}