package com.umc6th.kioki.kiosk

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface KioskRetrofitAPI {

    @GET("/users/kiosk")
    fun getKioskList(
        @Header("Authorization") token: String
    ): Call<KioskRetrofitClient2.KioskResponse>

}