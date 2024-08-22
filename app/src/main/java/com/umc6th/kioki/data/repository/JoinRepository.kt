package com.umc6th.kioki.data.repository

import com.umc6th.kioki.data.network.client.RetrofitClient
import com.umc6th.kioki.data.network.request.ExecuteJoinRequest
import com.umc6th.kioki.data.network.request.RequestVerifyCode
import com.umc6th.kioki.data.network.request.VerifyAuthCodeRequest
import com.umc6th.kioki.data.service.AuthService
import com.umc6th.kioki.data.service.JoinService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class JoinRepository(
) {
    private val authService = RetrofitClient.create(AuthService::class.java)

    private val joinService = RetrofitClient.create(JoinService::class.java)
    suspend fun requestPhoneNumber(phoneNumber: String) =
        authService.requestAuthCode(RequestVerifyCode(phoneNumber))


    suspend fun verifyAuthCode(phone: String, authCode: String) =
        authService.verifyAuthCode(
            VerifyAuthCodeRequest(phone, authCode)
        )

    suspend fun getPresignedUrl(fileName: String) = joinService.getPresignedUrl(fileName)


    suspend fun executeJoin(
        name: String,
        phone: String,
        imageName: String,
        birthday: String,
        introduction: String,
        kioskDifficulty: String
    ) = authService.executeJoin(
        ExecuteJoinRequest(
            name,
            phone,
            imageName,
            birthday,
            introduction,
            kioskDifficulty
        )
    )

    suspend fun uploadImageToS3(presignedUrl: String, image: MultipartBody.Part) =
        joinService.uploadImageToS3(presignedUrl, image)
}