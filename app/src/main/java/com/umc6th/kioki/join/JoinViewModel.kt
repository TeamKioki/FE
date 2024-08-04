package com.umc6th.kioki.join

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class JoinViewModel : ViewModel() {
    private val _kioskIssues = MutableStateFlow<List<KioskIssue>>(emptyList())
    val kioskIssues: StateFlow<List<KioskIssue>> = _kioskIssues.asStateFlow()

    init {
        // dummy data
        setKioskIssues(
            listOf(
                KioskIssue("글씨가 작아서"),
                KioskIssue("사용법을 배웠지만 까먹어서"),
                KioskIssue("디자인이 달라서"),
                KioskIssue("메뉴를 찾기 힘들어서"),
                KioskIssue("사용해 본 적이 없어서"),
                KioskIssue("뒷 사람이 부담되어서"),
                KioskIssue("처음 가보는 가게여서"),
                KioskIssue("소리가 안나와서")
            )
        )
    }

    private fun setKioskIssues(issues: List<KioskIssue>) {
        _kioskIssues.update { issues }
    }

    fun selectKioskIssue(issue: KioskIssue) {
        val updateIssues = _kioskIssues.value.map {
            if (it == issue) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        _kioskIssues.update { updateIssues }
    }
}

data class KioskIssue(
    val content: String = "",
    val isSelected: Boolean = false
)
