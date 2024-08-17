package com.umc6th.kioki.data.client

import com.umc6th.kioki.KiokiApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitClient {

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY  // 또는 다른 레벨로 설정
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dev.kioki.site")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}