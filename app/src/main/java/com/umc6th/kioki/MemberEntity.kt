package com.umc6th.kioki

import androidx.room.Entity
import androidx.room.PrimaryKey

// group을 담을 데이터 클래스 -> 통신 시, 엔티티로 변경할 것

@Entity(tableName = "MemberTable")
data class MemberEntity(
    var memberId: Int? = null,
    var userId: Int? = null,
    var memberImg: Int? = R.drawable.ic_home_user_character1,
    var memberName: String? = null,
    var memberNoteTitle: String? = null,
    var memberNoteText: String? = null
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}