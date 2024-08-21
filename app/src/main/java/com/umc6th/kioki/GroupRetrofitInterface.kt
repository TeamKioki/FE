package com.umc6th.kioki

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GroupRetrofitInterface {
    @GET("/groups/members")
    fun getMembers() : Call<GroupMembersResponse>

    @GET("/groups/members/paged")

}