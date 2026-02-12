package com.qijiavip.drama.data.model

data class NewsItem(
    val id: String,
    val title: String,
    val imageUrl: String,
    val readCount: Int,
    val category: String,
    val isUnlocked: Boolean = false,
    val unlockExpireTime: Long? = null,
    val content: String = "",
    val publishTime: String = "",
    val source: String = "齐加资讯"
)

data class BannerItem(
    val id: String,
    val imageUrl: String,
    val title: String,
    val targetUrl: String? = null
)

data class NewsCategory(
    val id: String,
    val name: String
)
