package com.umc6th.kioki

data class BrandItem(
    val brandName: String,
    val brandSpec: String,
    val brandLogoResId: Int,
    val pinCount: Int,
    val distance: String,
    var isBookmarked: Boolean = false // 즐겨찾기 상태를 저장하는 필드
)
