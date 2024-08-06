package com.umc6th.myapplication

// user를 담을 데이터 클래스 -> 통신 시, 엔티티로 변경할 것
data class User(
    var userImg: Int? = null,
    var userName: String? = null,
    var userDescription: String? = null
)
