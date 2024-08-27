package com.umc6th.kioki.group

import com.umc6th.kioki.R

object MemberLists {
    val members = listOf(
        MemberEntity(1,1, R.drawable.ic_home_user_character1, "키오키", "안녕하세요", "602호", true),
        MemberEntity(2,2, R.drawable.ic_home_user_character1, "유저2", "키오스크 초보자","", true),
        MemberEntity(3,3, R.drawable.ic_home_user_character1, "유저3", "안녕", "", true),
        MemberEntity(4,4, R.drawable.ic_home_user_character1, "유저4", "602호"),
        MemberEntity(5, 5, R.drawable.ic_home_user_character1, "유저5", "602호"),
        MemberEntity(6, 6, R.drawable.ic_home_user_character1, "유저6", "602호"),
        MemberEntity(7, 7, R.drawable.ic_home_user_character1, "유저7", "602호"),

        )
    fun syncIsGroupMember(userId: Int, isGroupMember: Boolean) {
        val member = members.find { it.userId == userId }
        member?.isGroupMember = isGroupMember
    }
}

object NotMemberLists {
    val members = listOf(
        NotMemberEntity(1,"키오키", "010-1234-5678", "안녕하세요", "R.drawable.ic_home_user_character1", true),
        NotMemberEntity(2,"유저2", "010-2345-6789", "키오스크 초보자", "R.drawable.ic_home_user_character1", true),
        NotMemberEntity(3,"유저3", "010-1111-2222", "안녕", "R.drawable.ic_home_user_character1", true),
        NotMemberEntity(4,"유저4", "010-0101-2223", "키오스크", "R.drawable.ic_home_user_character1"),
        NotMemberEntity(5,"유저5", "010-3333-5555", "안녕하세요", "R.drawable.ic_home_user_character1"),
        NotMemberEntity(6,"유저6", "010-4444-6666", "안녕하세요", "R.drawable.ic_home_user_character1"),
        NotMemberEntity(7,"유저7", "010-7777-7777", "안녕하세요", "R.drawable.ic_home_user_character1"),
        NotMemberEntity(8,"유저8", "010-8888-9999", "안녕하세요", "R.drawable.ic_home_user_character1"),
        NotMemberEntity(9,"유저9", "010-9999-7777", "안녕하세요", "R.drawable.ic_home_user_character1"),
        NotMemberEntity(10,"유저10", "010-0000-1000", "안녕하세요", "R.drawable.ic_home_user_character1"),

        )
    fun updateIsGroupMember(userId: Int, isGroupMember: Boolean) {
        val notMember = members.find { it.userId == userId }
        notMember?.isGroupMember = isGroupMember
        MemberLists.syncIsGroupMember(userId, isGroupMember)
    }
}