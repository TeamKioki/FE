package com.umc6th.kioki.data.repository

import com.umc6th.kioki.data.network.client.RetrofitClient
import com.umc6th.kioki.data.service.LoginService

class LoginRepository {
    private val loginService = RetrofitClient.create(LoginService::class.java)

    suspend fun login(phone: String, code: String) = loginService.login(phone, code)
}