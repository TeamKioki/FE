package com.umc6th.kioki

import com.google.gson.annotations.SerializedName

data class GroupMembersResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<GroupMembersResult>,
)

data class GroupMembersResult (
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("userId") val userId:Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profilePictureUrl") val profilePictureUrl: String,
    @SerializedName("noteTitle") val noteTitle: String,
    @SerializedName("noteText") val noteText: String
)

data class GroupMembersProfilePicture (
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<MemberEntity>,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("userId") val userId:Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profilePictureUrl") val profilePictureUrl: String,
    @SerializedName("noteTitle") val noteTitle: String,
    @SerializedName("noteText") val noteText: String,
    @SerializedName("color") val color:String,
    @SerializedName("fontSize") val fontSize: String
)



