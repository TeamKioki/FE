package com.umc6th.kioki.data.service

import com.umc6th.kioki.data.network.response.CommonResponse
import com.umc6th.kioki.data.network.response.PresignedUrlResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

interface JoinService {
    @GET("/api/v1/s3/presigned/upload")
    suspend fun getPresignedUrl(@Query ("fileName") fineName: String): Response<CommonResponse<PresignedUrlResponse>>

    @PUT
    @Multipart
    suspend fun uploadImageToS3(
        @Url presignedUrl: String,
        @Part image: MultipartBody.Part
    ): Response<Unit>
}