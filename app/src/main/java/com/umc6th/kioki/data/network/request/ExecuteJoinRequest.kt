package com.umc6th.kioki.data.network.request

data class VerifyAuthCodeRequest(
    val phone: String,
    val code: String
)

data class ExecuteJoinRequest(
    val name: String,
    val phone: String,
    val imageName: String,
    val birthDay: String,
    val introduction: String,
    val kioskDifficulty: String,
)
