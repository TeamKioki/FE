package com.umc6th.kioki

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.FragmentGroupHomeEditBinding
import com.umc6th.kioki.databinding.FragmentGroupHomeMoreAddBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupHomeMoreAddFragmentDialog: DialogFragment() {
    lateinit var binding: FragmentGroupHomeMoreAddBinding // 연결할 xml 파일 가져오기
    private var memberImg:Int = 0
    var memberName:String = ""
    var memberNoteTitle:String = ""
    var memberNoteText:String = ""
    private lateinit var apiService: GroupRetrofitInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 연결할 api 설정
        apiService = RetrofitClient.create(GroupRetrofitInterface::class.java) // baseurl 뒤에 붙일 url이 있는 인터페이스 파일 연결

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupHomeMoreAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setDimAmount(0.8f)  // 0.8f로 설정하면 더 어두운 효과를 줌
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_group_home_edit_rounded_bg) // 다이얼로그 테두리 둥글게

        // activity에서 전달해준 bundle 값 받기
        var bundle = arguments
        memberImg = bundle!!.getInt("MemberImg")
        memberName = bundle!!.getString("MemberName").toString()
        memberNoteTitle = bundle!!.getString("MemberNoteTitle").toString()
        memberNoteText = bundle!!.getString("MemberNoteText").toString()

        binding.moreAddMemberImgIv.setImageResource(memberImg)
        binding.moreAddMemberNameTv.setText(memberName)
        binding.moreAddInputTitleEt.setText(memberNoteTitle)
        binding.moreAddInputContentEt.setText(memberNoteText)

        // 'x' 아이콘 누르면 다이얼로그 닫히도록 하는 이벤트
        binding.moreAddCancelIv.setOnClickListener {
            dismiss()
        }

        // 그룹 추가 버튼 클릭 이벤트
        binding.moreAddGroupBtn.setOnClickListener {
            addGroupMember()
        }

    }

    fun addGroupMember() {
        // 서버에서 제공받은 Access Token
        val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGhvbmUiOiIwMTAxMjM0NTY3OCIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MjM3MTU1NTgsImV4cCI6MTcyNjMwNzU1OH0._TI2xGiWqvtNp9ooaf_rRo8puTA1tAZqKoAjADmKwOA"
        val groupMember = GroupMember (
            memberId = 1,
            userId = 3,
            nickname = memberName,
            profilePictureUrl = "",
            noteTitle = memberNoteTitle,
            noteText = memberNoteText,
        )
        // API 호출
        postMembers(accessToken, groupMember)  // 멤버 목록 가져오기

    }
    // API
    private fun postMembers(token: String, groupMember: GroupMember) {
        apiService.postMembers("Bearer $token", groupMember).enqueue(object :
            Callback<GroupMembersResponse> {
            override fun onResponse(call: Call<GroupMembersResponse>, response: Response<GroupMembersResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val result = response.body()
                    Log.d("통신", "GroupMembers Response: $result")
                    // 결과 처리

                } else {
                    Log.e("통신", "GroupMembers Response 실패: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GroupMembersResponse>, t: Throwable) {
                Log.e("통신", "통신 실패: ${t.message}", t)
            }
        })
    }

}