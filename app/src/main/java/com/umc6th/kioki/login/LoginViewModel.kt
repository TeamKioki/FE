package com.umc6th.kioki.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umc6th.kioki.KiokiApplication
import com.umc6th.kioki.data.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository = LoginRepository()) : ViewModel() {

    private val _loginState = MutableLiveData<Boolean>(false)
    val loginState = _loginState


    fun executeLogin(phone: String, code: String) {
        viewModelScope.launch {
            val response = loginRepository.login(phone, code)
            if (response.isSuccessful) {
                val body = response.body()!!
                KiokiApplication.tokenPrefs.setAccessToken(body.data.accessToken)
                KiokiApplication.tokenPrefs.setRefreshToken(body.data.refreshToken)
                _loginState.postValue(true)
            } else {
                // handle error
            }
        }
    }

    fun requestAuthCode(phone: String) {
        viewModelScope.launch {
            val response = loginRepository.requestAuthCode(phone)
            if (response.isSuccessful) {

                Log.d(TAG, "requestAuthCode: success")
                // handle success
            } else {
                // handle error
                Log.d(TAG, "requestAuthCode: ${response.message()} ${response.code()}")
            }
        }
    }

    fun verifyAuthCode(phone: String, code: String) {
        viewModelScope.launch {
            val response = loginRepository.verifyAuthCode(phone, code)
            if (response.isSuccessful) {
                _loginState.postValue(true)
            } else {
                // handle error
            }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}