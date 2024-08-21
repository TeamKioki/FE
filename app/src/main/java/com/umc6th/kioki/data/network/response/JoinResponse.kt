package com.umc6th.kioki.data.network.response

import com.google.gson.annotations.SerializedName


data class RequestAuthCodeResponse(
    @SerializedName("phone") val phone: String,
    @SerializedName("code") val code: String
)

data class AuthCodeVerifyResponse(
    @SerializedName("phone") val phone: String,
    @SerializedName("isCodeValid") val isCodeValid: Boolean
)

