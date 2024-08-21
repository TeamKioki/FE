package com.umc6th.kioki.data.service

import com.umc6th.kioki.data.network.request.ExecuteJoinRequest
import com.umc6th.kioki.data.network.request.RequestVerifyCode
import com.umc6th.kioki.data.network.request.VerifyAuthCodeRequest
import com.umc6th.kioki.data.network.response.CommonResponse
import com.umc6th.kioki.data.network.response.RequestAuthCodeResponse
import com.umc6th.kioki.data.network.response.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/v1/auth/login")
    suspend fun login(phone: String, code: String): Response<CommonResponse<TokenResponse>>

    @POST("/api/v1/auth/sms/send")
    suspend fun requestAuthCode(@Body phoneNumber: RequestVerifyCode): Response<CommonResponse<RequestAuthCodeResponse>>

    @POST("/api/v1/auth/sms/verify")
    suspend fun verifyAuthCode(@Body verifyAuthCodeRequest: VerifyAuthCodeRequest): Response<CommonResponse<VerifyAuthCodeRequest>>

    @POST("/api/v1/auth/join")
    suspend fun executeJoin(executeJoinRequest: ExecuteJoinRequest): Response<CommonResponse<TokenResponse>>
}