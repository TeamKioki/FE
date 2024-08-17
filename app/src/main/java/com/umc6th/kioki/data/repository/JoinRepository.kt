package com.umc6th.kioki.data.repository

import com.umc6th.kioki.data.network.client.RetrofitClient
import com.umc6th.kioki.data.network.request.ExecuteJoinRequest
import com.umc6th.kioki.data.network.request.RequestVerifyCode
import com.umc6th.kioki.data.network.request.VerifyAuthCodeRequest
import com.umc6th.kioki.data.service.AuthService

class JoinRepository(
) {
    private val joinService = RetrofitClient.create(AuthService::class.java)

    suspend fun requestPhoneNumber(phoneNumber: String) =
        joinService.requestAuthCode(RequestVerifyCode(phoneNumber))


    suspend fun verifyAuthCode(phone: String, authCode: String) =
        joinService.verifyAuthCode(
            VerifyAuthCodeRequest(phone, authCode)
        )


    suspend fun executeJoin(
        name: String,
        phone: String,
        imageName: String,
        birthday: String,
        introduction: String,
        kioskDifficulty: String
    ) = joinService.executeJoin(
        ExecuteJoinRequest(
            name,
            phone,
            imageName,
            birthday,
            introduction,
            kioskDifficulty
        )
    )
}