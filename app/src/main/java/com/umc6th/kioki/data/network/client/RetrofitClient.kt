package com.umc6th.kioki.data.network.client

import com.umc6th.kioki.data.network.Interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dev.kioki.site")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val client = OkHttpClient.Builder()
    .addInterceptor(AuthInterceptor())
    .build()

    private val retrofitWithInterceptor = Retrofit.Builder()
        .client(client)
        .baseUrl("https://dev.kioki.site")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}