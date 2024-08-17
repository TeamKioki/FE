package com.umc6th.kioki

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface GroupRetrofitInterface {
    @GET("/groups/members/paged")
    fun getMembersPaged(
        @Header("Authorization") token: String,
        @Query("page") page: Int
    ) : Call<GroupMembersPagedResponse>

    @GET("/groups/members")
    fun getMembers(
        @Header("Authorization") token: String,
    ) : Call<GroupMembersResponse>

    @POST("/groups/members")
    fun postMembers(
        @Header("Authorization") token: String,
        @Body groupMember: GroupMember
    ) : Call<GroupMembersResponse>
}