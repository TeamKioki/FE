package com.umc6th.myapplication

// group을 담을 데이터 클래스 -> 통신 시, 엔티티로 변경할 것
data class Group(
    var groupImg: Int? = null,
    var groupName: String? = null,
    var groupDescription1: String? = null,
    var groupDescription2: String? = null
)
