package com.umc6th.kioki

import com.google.gson.annotations.SerializedName

class KioskRetrofitClient2 {

    // 유저의 키오스크 목록 조회
    data class KioskResponse(
        @SerializedName("isSuccess")
        val isSuccess: Boolean,
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: List<KioskModel>
    )

    data class KioskModel(
        @SerializedName("modelId")
        val modelId: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("imageUrl")
        val imageUrl: String,
        @SerializedName("level")
        val level: Int,
        @SerializedName("rate")
        val rate: Int,
        @SerializedName("stores_id")
        val storesId: List<Int>
    )

    // 키오스크 모델 추가


    // 유저 키오스크 모델 삭제


    // 음식점 가게 목록


    // 음식점 가게 필터 목록


    // 키오스크 모델 목록


}