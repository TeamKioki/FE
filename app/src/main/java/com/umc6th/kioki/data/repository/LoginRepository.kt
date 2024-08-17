package com.umc6th.kioki.data.repository

import com.umc6th.kioki.data.network.client.RetrofitClient
import com.umc6th.kioki.data.network.request.RequestVerifyCode
import com.umc6th.kioki.data.network.request.VerifyAuthCodeRequest
import com.umc6th.kioki.data.service.AuthService
import com.umc6th.kioki.data.service.JoinService
import com.umc6th.kioki.data.service.LoginService

class LoginRepository {
    private val authService = RetrofitClient.create(AuthService::class.java)


    suspend fun login(phone: String, code: String) = authService.login(phone, code)

    suspend fun requestAuthCode(phone: String) = authService.requestAuthCode(RequestVerifyCode(phone))

    suspend fun verifyAuthCode(phone: String, code: String) = authService.verifyAuthCode(
        VerifyAuthCodeRequest( phone, code)
    )
}