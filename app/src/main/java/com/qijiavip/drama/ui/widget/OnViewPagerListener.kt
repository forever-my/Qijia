package com.qijiavip.drama.ui.widget

interface OnViewPagerListener {
    fun onInitComplete()
    fun onPageRelease(isNext: Boolean, position: Int)
    fun onPageSelected(position: Int, isBottom: Boolean)
}
