package com.umc6th.kioki.data.request

import com.google.gson.annotations.SerializedName

data class RequestVerifyCode(
    @SerializedName("phone") val phone: String
)