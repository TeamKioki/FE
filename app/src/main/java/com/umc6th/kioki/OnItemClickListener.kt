package com.umc6th.kioki

interface OnItemClickListener {
    fun onItemClick(member: GroupMembersResult)
    fun onAddButtonClick() // 그룹 추가 버튼을 눌렀을 때
}