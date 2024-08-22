package com.umc6th.kioki

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
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
        @Body userIdRequest: UserIdRequest
    ) : Call<GroupMemberResponse>

    @DELETE("/groups/members/{memberId}")
    fun deleteMember(
        @Header("Authorization") token: String,
        @Path("memberId") memberId: Int
    ) : Call<GroupMembersResponse>

    @PATCH("/groups/members/{memberId}")
    fun modifyMember(
        @Header("Authorization") token: String,
        @Path("memberId") memberId: Int,
        @Body requestBody: RequestBody
    ) : Call<GroupMemberResponse>

    @GET("/groups/members/search")
    fun searchMembers(
        @Header("Authorization") token: String,
        @Query("nickname") nickname: String
    ): Call<GroupMembersResponse>

    @GET("/groups/search")
    fun searchNotMembers(
        @Header("Authorization") token: String,
        @Query("query") nickname: String
    ): Call<NotGroupMembersResponse>

}