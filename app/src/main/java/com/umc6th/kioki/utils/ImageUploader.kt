package com.umc6th.kioki.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import java.io.File
import java.io.IOException

class ImageUploader(private val context: Context) {

    private val client = OkHttpClient()

    fun uploadImage(presignedUrl: String, imageUri: Uri) {
        val file = File(imageUri.path!!)

        // RequestBody를 생성합니다.
        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())

        // Request를 생성합니다.
        val request = Request.Builder()
            .url(presignedUrl)
            .put(requestBody)
            .build()

        // 비동기 요청을 보냅니다.
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ImageUploader", "Upload failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("ImageUploader", "Upload successful!")
                } else {
                    Log.e("ImageUploader", "Upload failed: ${response.message}")
                }
            }
        })
    }
}