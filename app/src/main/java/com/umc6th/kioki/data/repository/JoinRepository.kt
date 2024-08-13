package com.umc6th.kioki.data.repository

import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.data.request.ExecuteJoinRequest
import com.umc6th.kioki.data.request.VerifyAuthCodeRequest
import com.umc6th.kioki.data.service.JoinService

class JoinRepository(
) {
    private val joinService = RetrofitClient.create(JoinService::class.java)

    suspend fun requestPhoneNumber(phoneNumber: String) =
        joinService.requestAuthCode(phoneNumber)


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