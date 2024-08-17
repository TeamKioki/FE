package com.umc6th.kioki.data.network.Interceptor

import com.umc6th.kioki.KiokiApplication
import okhttp3.Interceptor

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val accessToken = KiokiApplication.tokenPrefs.getAccessToken("")

        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(newRequest)
    }
}