package com.umc6th.kioki

// group을 담을 데이터 클래스 -> 통신 시, 엔티티로 변경할 것
data class Group(
    var groupImg: Int? = R.drawable.ic_home_user_character1,
    var groupName: String? = null,
    var groupDescription1: String? = null,
    var groupDescription2: String? = null
)
