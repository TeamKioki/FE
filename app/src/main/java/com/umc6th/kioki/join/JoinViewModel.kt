package com.umc6th.kioki.join

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc6th.kioki.data.repository.JoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JoinViewModel(
    private val joinRepository: JoinRepository = JoinRepository()
) : ViewModel(
) {
    private val _kioskIssues = MutableStateFlow<List<KioskIssue>>(emptyList())
    val kioskIssues: StateFlow<List<KioskIssue>> = _kioskIssues.asStateFlow()

    private val _isAuthCodeVerified = MutableLiveData<VerifyAuthCodeResult>(VerifyAuthCodeResult.Empty)
    val isAuthCodeVerified: LiveData<VerifyAuthCodeResult> = _isAuthCodeVerified

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

    fun requestAuthCode(phoneNumber: String) {
        viewModelScope.launch {
            val response = joinRepository.requestPhoneNumber(phoneNumber)
            if (response.isSuccessful) {
                Log.d(TAG, "requestAuthCode: success")
            } else {
                Log.d(TAG, "requestAuthCode: ${response.message()}")

            }
        }
    }

    fun verifyAuthCode(phone: String, authCode: String) {
        viewModelScope.launch {
            val response = joinRepository.verifyAuthCode(phone, authCode)
            if (response.isSuccessful) {
                _isAuthCodeVerified.value = VerifyAuthCodeResult.Success
            } else {
                _isAuthCodeVerified.value = VerifyAuthCodeResult.Failure
            }
        }
    }

    fun executeJoin(
        name: String,
        phone: String,
        imageName: String,
        birthday: String,
        introduction: String,
        kioskDifficulty: String

    ) {
        viewModelScope.launch {
            joinRepository.executeJoin(
                name,
                phone,
                imageName,
                birthday,
                introduction,
                kioskDifficulty
            )
        }


    }
    companion object {
        private const val TAG = "JoinViewModel"
    }
}

data class KioskIssue(
    val content: String = "",
    val isSelected: Boolean = false
)

sealed class VerifyAuthCodeResult {

    object Empty : VerifyAuthCodeResult()
    object Success : VerifyAuthCodeResult()
    object Failure : VerifyAuthCodeResult()
}
