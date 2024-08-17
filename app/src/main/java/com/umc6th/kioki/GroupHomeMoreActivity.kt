package com.umc6th.kioki

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.ActivityGroupHomeMoreBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupHomeMoreActivity:AppCompatActivity(), OnItemClickListener {
    lateinit var binding:ActivityGroupHomeMoreBinding // 연결할 xml 파일
    private lateinit var groupList: MutableList<MemberEntity> // 그룹 리스트
    private lateinit var groupMoreListAdapter: GroupMoreRvAdapter // 어댑터
    private lateinit var apiService: GroupRetrofitInterface

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

        // 리싸이클러뷰 초기화
        setupRecyclerView()

        // 서버에서 제공받은 Access Token
        val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGhvbmUiOiIwMTAxMjM0NTY3OCIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MjM3MTU1NTgsImV4cCI6MTcyNjMwNzU1OH0._TI2xGiWqvtNp9ooaf_rRo8puTA1tAZqKoAjADmKwOA"
        // API 호출
        fetchMembers(accessToken)  // 멤버 목록 가져오기

//        groupMoreListAdapter = GroupMoreRvAdapter(groupList, this) // 어댑터 초기화
//        rv.adapter = groupMoreListAdapter // 어댑터와 연결
//
//        // DiffUtil 적용 후 데이터 추가
//        groupMoreListAdapter.differ.submitList(groupList)

    }
    private fun setupRecyclerView() {
        // 리싸이클러뷰를 어댑터와 연결
        val rv = binding.groupMoreRecyclerview
        rv.layoutManager = LinearLayoutManager(this) // context 전달

        groupMoreListAdapter = GroupMoreRvAdapter(groupList, this) // 어댑터 초기화
        rv.adapter = groupMoreListAdapter // 어댑터와 연결
    }

    // API
    private fun fetchMembers(token: String) {
        apiService.getMembers("Bearer $token").enqueue(object :
            Callback<GroupMembersResponse> {
            override fun onResponse(call: Call<GroupMembersResponse>, response: Response<GroupMembersResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val result = response.body()
                    Log.d("통신", "GroupMembers Response: $result")
                    // 멤버 목록 처리
                    val groupMembers = result?.result
                    if(groupMembers!=null && groupMembers.isNotEmpty()) {
                        groupList.clear()
                        groupList.addAll(groupMembers.map { member ->
                            MemberEntity(
                                memberImg = R.drawable.ic_home_user_character1,
                                memberName = member.nickname,
                                memberNoteTitle = member.noteTitle,
                                memberNoteText = member.noteText,
                            )
                        })
                        Log.d("멤버목록", groupList.toString())

                        groupMoreListAdapter.differ.submitList(groupList)

                    }
                } else {
                    Log.e("통신", "GroupMembers Response 실패: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GroupMembersResponse>, t: Throwable) {
                Log.e("통신", "통신 실패: ${t.message}", t)
            }
        })
    }

    override fun onItemClick(member: MemberEntity) {
//        val dialog = GroupHomeMoreAddFragmentDialog().show(
//            supportFragmentManager, "GroupHomeMoreAddFragmentDialog"
//        )
        val dialog = GroupHomeMoreAddFragmentDialog()
        var bundle = Bundle() // 다이얼로그에 전달할 Bundle 생성
        bundle.putInt("MemberImg", member.memberImg!!)
        bundle.putString("MemberName", member.memberName)
        bundle.putString("MemberNoteTitle", member.memberNoteTitle)
        bundle.putString("MemberNoteText", member.memberNoteText)
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "GroupHomeMoreAddFragmentDialog")

    }

    override fun onAddButtonClick() {
        return
    }
}