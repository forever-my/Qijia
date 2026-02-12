package com.qijiavip.drama.data.remote.dto

data class PageResult<T>(
    val list: List<T>,
    val page: Int,
    val size: Int,
    val total: Long
)
