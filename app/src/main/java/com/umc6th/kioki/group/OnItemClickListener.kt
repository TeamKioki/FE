package com.umc6th.kioki.group

interface OnItemClickListener {
    fun onItemClick(member: MemberEntity)
    fun onAddButtonClick() // 그룹 추가 버튼을 눌렀을 때
}

interface OnMoreGroupClickListener {
    fun onItemClick(member: NotMemberEntity)
}

interface OnGroupMemberChangeListener {
    fun onGroupMemberChanged()
}

interface DialogListener {
    fun onModifiedDataReceived(data1: String, data2: Int, data3: Boolean)
}