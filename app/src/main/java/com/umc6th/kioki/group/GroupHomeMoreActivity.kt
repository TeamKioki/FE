package com.umc6th.kioki.group

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.ActivityGroupHomeMoreBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupHomeMoreActivity:AppCompatActivity(), OnMoreGroupClickListener, OnGroupMemberChangeListener {
    lateinit var binding:ActivityGroupHomeMoreBinding // 연결할 xml 파일
    lateinit var groupList: MutableList<NotMemberEntity> // 그룹 리스트
    lateinit var groupMoreRvAdapter: GroupMoreRvAdapter // 어댑터
    private lateinit var apiService: GroupRetrofitInterface
    val accessToken =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInBob25lIjoiMDEwODI0NzMwMTAiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzI0MzE5MjM5LCJleHAiOjE3MjY5MTEyMzl9.Zwz108s5qKDBo02nm16H_Ma_P0CnkUybG66XbkOk9_A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃 설정
        binding = ActivityGroupHomeMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기 버튼 이벤트
        val backBtn = binding.groupMoreBackBtnIv
        backBtn.setOnClickListener {
            finish()
        }

        // 연결할 api 설정
        apiService = RetrofitClient.create(GroupRetrofitInterface::class.java) // baseurl 뒤에 붙일 url이 있는 인터페이스 파일 연결

        groupList = mutableListOf() // 그룹리스트 초기화

        // 리싸이클러뷰, 어댑터 초기화
//        setupRecyclerView()

        // groupList에 memberLists 목록 추가하기
        groupList.addAll(NotMemberLists.members)

//        // DiffUtil 적용 후 데이터 추가
//        groupMoreRvAdapter.differ.submitList(groupList)

        // API 호출
        fetchMembers(accessToken, "010")  // 멤버 목록 가져오기

    }

    override fun onResume() {
        super.onResume()
        // 리싸이클러뷰 설정
        setupRecyclerView()

        // DiffUtil 적용 후 데이터 추가
        groupMoreRvAdapter.differ.submitList(groupList)

        Log.d("멤버목록", "추천친구 목록: ${groupList}")
    }
    private fun setupRecyclerView() {
        // 리싸이클러뷰를 어댑터와 연결
        val rv = binding.groupMoreRecyclerview
        rv.layoutManager = LinearLayoutManager(this) // context 전달

        groupMoreRvAdapter = GroupMoreRvAdapter(groupList, this) // 어댑터 초기화
        rv.adapter = groupMoreRvAdapter // 어댑터와 연결
    }


    // API
    private fun fetchMembers(token: String, query:String) {
//        apiService.searchNotMembers("Bearer $token", query).enqueue(object :
//            Callback<NotGroupMembersResponse> {
//            override fun onResponse(call: Call<NotGroupMembersResponse>, response: Response<NotGroupMembersResponse>) {
//                if (response.isSuccessful && response.code() == 200) {
//                    val result = response.body()
//                    Log.d("통신", "GroupMembers Response: $result")
//                    // 멤버 목록 처리
//                    val groupMembers = result?.result
//                    Log.d("멤버목록", groupMembers.toString())
//                    if(groupMembers!=null && groupMembers.isNotEmpty()) {
//                        groupList.clear()
//                        groupList.addAll(groupMembers.map { member ->
//                            NotMemberEntity(
//                                userId = member.userId,
//                                name = member.name,
//                                phone  = member.phone,
//                                introduction = member.introduction,
//                                imageName = member.imageName,
//                                isGroupMember = member.isGroupMember
//
//                            )
//                        })
//                        Log.d("멤버목록", groupList.toString())
//
//                        groupMoreRvAdapter.differ.submitList(groupList)
//                    }
//                } else {
//                    Log.e("통신", "GroupMembers Response 실패: ${response.code()} - ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<NotGroupMembersResponse>, t: Throwable) {
//                Log.e("통신", "통신 실패: ${t.message}", t)
//            }
//        })
    }

    override fun onItemClick(member: NotMemberEntity) {
        val dialog = GroupHomeMoreAddFragmentDialog()
        var bundle = Bundle() // 다이얼로그에 전달할 Bundle 생성
        bundle.putInt("UserId", member.userId!!)
        //bundle.putInt("MemberImg", member.imageName!!)
        bundle.putString("MemberName", member.name)
        bundle.putString("MemberNoteTitle", member.phone)
        bundle.putString("MemberNoteText", member.introduction)
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "GroupHomeMoreAddFragmentDialog")

    }

    override fun onGroupMemberChanged() {
        groupMoreRvAdapter.notifyDataSetChanged() // 리사이클러뷰 갱신
    }
}