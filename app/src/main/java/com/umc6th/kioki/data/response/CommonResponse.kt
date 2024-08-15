package com.umc6th.kioki.data.response

import com.google.gson.annotations.SerializedName

data class CommonResponse<out T>(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val data: T
)
