package com.umc6th.kioki.group

import com.umc6th.kioki.R

object DummyLists {
    val notices = listOf(
        NoticeEntity("[공지] 키오스크 신규 추가 목록 안내", "2024.08.22"),
        NoticeEntity("[공지] 글씨 크기 적용 관련 안내", "2024.07.23"),
        NoticeEntity("[공지] 키오스크 평가 관련 안내", "2024.07.10"),
        NoticeEntity("[공지] 통합 서비스 이용약관 계정 안내", "2024.06.22"),
        NoticeEntity("[공지] 앱 최소 지원 버전 변경 안내", "2024.06.05"),
    )
    val reivews = listOf(
        ReviewEntity(R.drawable.burgerking, "버거킹", "햄버거/패스트푸드점", 4.0,"연습을 하고 갔지만 메뉴가 너무 많고 주문과정이 복잡하게 느껴졌어요"),
        ReviewEntity(R.drawable.burgerking,"메가커피", "햄버거/패스트푸드점", 5.0, "연습을 하고 갔지만 메뉴가 너무 많고 주문과정이 복잡하게 느껴졌어요"),
        ReviewEntity(R.drawable.burgerking,"버거킹", "햄버거/패스트푸드점", 4.0, "연습을 하고 갔지만 메뉴가 너무 많고 주문과정이 복잡하게 느껴졌어요")

    )
}
