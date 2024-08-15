package com.umc6th.kioki.data.service

import com.umc6th.kioki.data.response.CommonResponse
import com.umc6th.kioki.data.response.TokenResponse
import retrofit2.Response
import retrofit2.http.POST

interface LoginService {
    @POST("/api/v1/auth/login")
    suspend fun login(phone: String, code: String): Response<CommonResponse<TokenResponse>>
}