package com.umc6th.kioki.join

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc6th.kioki.KiokiApplication
import com.umc6th.kioki.data.repository.JoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class JoinViewModel(
    private val joinRepository: JoinRepository = JoinRepository()
) : ViewModel() {
    private val _kioskIssues = MutableStateFlow<List<KioskIssue>>(emptyList())
    val kioskIssues: StateFlow<List<KioskIssue>> = _kioskIssues.asStateFlow()

    private val _isAuthCodeVerified = MutableLiveData<VerifyAuthCodeResult>(VerifyAuthCodeResult.Empty)
    val isAuthCodeVerified: LiveData<VerifyAuthCodeResult> = _isAuthCodeVerified

    private val _presignedUrl = MutableLiveData<String>("")
    val presignedUrl: LiveData<String> = _presignedUrl

    private var imageName: String = ""
    private var userName: String = ""
    private var userBirthDay: String = ""
    private var userPhone: String = ""
    private var userIntroduction: String = ""
    private var userDifficulty: String = ""

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

    fun getPresignedUrl(fileName: String) {
        viewModelScope.launch {
            val response = joinRepository.getPresignedUrl(fileName)
            if (response.isSuccessful) {
                Log.d(TAG, "getPresignedUrl: success")
                _presignedUrl.value = response.body()!!.data.url
                imageName = response.body()!!.data.keyName
            } else {
                Log.d(TAG, "getPresignedUrl: ${response.message()}")
            }
        }
    }

    fun uploadImageToS3(presignedUrl: String, body: MultipartBody.Part) {


        viewModelScope.launch {
            val response = joinRepository.uploadImageToS3(presignedUrl, body)
            if (response.isSuccessful) {
                Log.d(TAG, "uploadImageToS3: success")
            } else {
                Log.d(TAG, "uploadImageToS3: ${response.message()}")
            }
        }
    }



    fun selectKioskIssue(index: Int) {
        val updateIssues = _kioskIssues.value.mapIndexed { idx, it ->
            if (idx == index) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }
        _kioskIssues.update { updateIssues }
    }

    fun requestAuthCode(phoneNumber: String) {
        viewModelScope.launch {
            Log.d(TAG, "requestAuthCode: $phoneNumber")
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

    fun executeJoin() {
        viewModelScope.launch {
            val response = joinRepository.executeJoin(
                name = userName,
                phone = userPhone,
                imageName = imageName,
                birthday = userBirthDay,
                introduction = userIntroduction,
                kioskDifficulty = userDifficulty
            )
            if (response.isSuccessful) {
                val body = response.body()!!
                KiokiApplication.tokenPrefs.setAccessToken(body.data.accessToken)
                KiokiApplication.tokenPrefs.setRefreshToken(body.data.refreshToken)
            } else {
                // handle error
            }
        }
    }

    fun setUserInfo(name: String, birthDay: String, phone: String) {
        userName = name
        userBirthDay = birthDay
        userPhone = phone
    }

    fun setUserIntroduction(introduction: String) {
        userIntroduction = introduction
    }
    fun setUserDifficulty(difficulty: String) {
        userDifficulty = difficulty
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
