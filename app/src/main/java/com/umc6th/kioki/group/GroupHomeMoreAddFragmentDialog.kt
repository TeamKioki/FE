package com.umc6th.kioki.group

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.umc6th.kioki.R
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.FragmentGroupHomeMoreAddBinding
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupHomeMoreAddFragmentDialog: DialogFragment() {
    lateinit var binding: FragmentGroupHomeMoreAddBinding // 연결할 xml 파일 가져오기
    private var memberImg:Int = 0
    var memberName:String = ""
    var memberNoteTitle:String = ""
    var memberNoteText:String = ""
    var memberId : Int = 0
    var userId : Int = 0
    private lateinit var apiService: GroupRetrofitInterface
    private var listener: OnGroupMemberChangeListener? = null


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
        memberId = bundle!!.getInt("MemberId")
        userId = bundle!!.getInt("UserId")
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
            val currentText = binding.moreAddGroupBtn.text.toString()
            if(currentText == "그룹 추가") {
                addGroupMember()
            } else if(currentText == "그룹 삭제") {
                deleteGroupMember()
            }
        }

//        // 어댑터에 변경된 데이터를 갱신
//        val activity = activity as GroupHomeMoreActivity
//        val member = activity.groupList.find { it.userId == userId }
//
//        member?.let {
//            val position = activity.groupList.indexOf(it)
//            activity.groupMoreRvAdapter.notifyItemChanged(position)
//
//            // UI 업데이트 (버튼 텍스트 변경)
//            if (it.isGroupMember == true) {
//                binding.moreAddGroupBtn.text = "그룹 삭제"
//            }
//        }
    }

    override fun onResume() {
        super.onResume()

        // 어댑터에 변경된 데이터를 갱신
        val activity = activity as GroupHomeMoreActivity
        val member = activity.groupList.find { it.userId == userId }

        member?.let {
            val position = activity.groupList.indexOf(it)
            activity.groupMoreRvAdapter.notifyItemChanged(position)

            // UI 업데이트 (버튼 텍스트 변경)
            if (it.isGroupMember == true) {
                binding.moreAddGroupBtn.text = "그룹 삭제"
            } else {
                binding.moreAddGroupBtn.text = "그룹 추가"
            }
        }

    }
    fun setOnGroupMemberChangeListener(listener: OnGroupMemberChangeListener) {
        this.listener = listener
    }

    fun addGroupMember() {
        // 서버에서 제공받은 Access Token
        val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInBob25lIjoiMDEwODI0NzMwMTAiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzI0MzE5MjM5LCJleHAiOjE3MjY5MTEyMzl9.Zwz108s5qKDBo02nm16H_Ma_P0CnkUybG66XbkOk9_A"
        val userIdRequest = UserIdRequest(userId)

        // API 호출
        //postMembers(accessToken, userIdRequest)  // 멤버 목록 가져오기

        updateGroupMemberStatus(true)
    }
    fun deleteGroupMember() {
        updateGroupMemberStatus(false)
    }

    // API
    private fun postMembers(token: String, userIdRequest: UserIdRequest) {
        apiService.postMembers("Bearer $token", userIdRequest).enqueue(object :
            Callback<GroupMemberResponse> {
            override fun onResponse(call: Call<GroupMemberResponse>, response: Response<GroupMemberResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val result = response.body()
                    Log.d("통신", "GroupMembers Response: $result")

                    // 멤버의 isGroupMember 값을 true로 변경
                    updateGroupMemberStatus(true)


                } else {
                    Log.e("통신", "GroupMembers Response 실패: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GroupMemberResponse>, t: Throwable) {
                Log.e("통신", "통신 실패: ${t.message}", t)
            }
        })
    }

    private fun updateGroupMemberStatus(isGroupMember: Boolean) {
        NotMemberLists.updateIsGroupMember(userId, isGroupMember)


        // 멤버가 그룹에 추가된 후 isGroupMember 값을 true로 변경
        val groupHomeMoreActivity = activity as GroupHomeMoreActivity
        val member = groupHomeMoreActivity.groupList.find { it.userId == userId }

        member?.isGroupMember = isGroupMember
        groupHomeMoreActivity.onGroupMemberChanged() // 바로 데이터 갱신
        listener?.onGroupMemberChanged()



        //Log.d("멤버목록", "추천친구 목록: ${groupHomeMoreActivity.groupList}")


        dismiss()
    }
}