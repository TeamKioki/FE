package com.umc6th.kioki

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object KioskRetrofitObject {
    private const val BASE_URL = "https://dev.kioki.site"

    private val getRetrofit by lazy {

        val clientBuilder = OkHttpClient.Builder()

        clientBuilder.readTimeout(30, TimeUnit.SECONDS) // 읽기 타임아웃: 30초
        clientBuilder.writeTimeout(30, TimeUnit.SECONDS) // 쓰기 타임아웃: 30초

        val client = clientBuilder.build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val getRetrofitService : KioskRetrofitAPI by lazy { getRetrofit.create(KioskRetrofitAPI::class.java) }
}