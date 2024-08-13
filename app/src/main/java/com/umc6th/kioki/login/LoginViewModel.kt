package com.umc6th.kioki.login

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

}