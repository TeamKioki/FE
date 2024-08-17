package com.umc6th.kioki

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.ActivityGroupHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GroupHomeActivity: AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityGroupHomeBinding
    private lateinit var groupList: MutableList<MemberEntity>
    private lateinit var groupListAdapter: GroupRvAdapter
    private lateinit var apiService: GroupRetrofitInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃 설정
        binding = ActivityGroupHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("그룹홈", "onCreate called")
        // 연결할 api 설정
        apiService = RetrofitClient.create(GroupRetrofitInterface::class.java) // baseurl 뒤에 붙일 url이 있는 인터페이스 파일 연결

        // 서버에서 제공받은 Access Token
        val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGhvbmUiOiIwMTAxMjM0NTY3OCIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MjM3MTU1NTgsImV4cCI6MTcyNjMwNzU1OH0._TI2xGiWqvtNp9ooaf_rRo8puTA1tAZqKoAjADmKwOA"
        // API 호출
        //fetchMembers(accessToken)  // 멤버 목록 가져오기

        // 뒤로가기 버튼 이벤트
        val backBtn = binding.groupHeaderNavBackIv
        backBtn.setOnClickListener {
            finish()
        }

        groupList = mutableListOf()
        // 리싸이클러뷰
        val rv = binding.groupRecyclerview
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // rv에 어댑터 연결
        groupListAdapter = GroupRvAdapter(groupList, this)
        rv.adapter = groupListAdapter

        // DiffUtil 적용 후 데이터 추가
        //groupListAdapter.differ.submitList(groupList)

        // 삭제 편집 버튼 누르면 모든 리스트 왼쪽에 삭제 버튼 뜨게 하기
        binding.groupDeleteBtnIv.setOnClickListener {
            groupListAdapter.toggleDeleteButtonVisibility()
        }

        // 검색 기능
        binding.groupSearchEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                var searchText:String = binding.groupSearchEt.text.toString()
                // 필터링된 리스트 생성
//                val filteredList = if (searchText.isEmpty()) {
//                    MemberLists.groups // 아무것도 입력되지 않을 때는 모든 데이터를 사용
//                } else {
//                    MemberLists.groups.filter {
//                        it.memberName!!.contains(searchText, ignoreCase = true)
//                    }
//                }

                // 필터링된 결과를 어댑터에 전달
                //groupListAdapter.differ.submitList(filteredList)


            }

        })
    }

    // API
    private fun fetchMembers(token: String) {
        apiService.getMembers("Bearer $token").enqueue(object :
            Callback<GroupMembersResponse> {
            override fun onResponse(call: Call<GroupMembersResponse>, response: Response<GroupMembersResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val result = response.body()
                    Log.d("통신", "GroupMembers Response: $result")
                    // 결과 처리
                    if (result != null) {
                        // 데이터가 성공적으로 반환된 경우
                        Log.d("멤버통신", "Members: ${result.result}")
                    } else {
                        Log.d("멤버통신", "Result is empty or null")
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

    // 아이템 클릭시
    override fun onItemClick(member: MemberEntity) {
        val dialog = GroupHomeEditFragmentDialog()
        var bundle = Bundle() // 다이얼로그에 전달할 Bundle 생성
//        bundle.putString("MemberName", member.nickname)
//        bundle.putString("MemberNoteTitle", member.noteTitle)
//        bundle.putString("MemberNoteText", member.noteText)
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "GroupHomeEditFragmentDialog")
    }

    // 그룹추가 버튼 클릭 시 GroupHomeMoreActivity로 넘어가는 이벤트
    override fun onAddButtonClick() {
        val intent = Intent(this, GroupHomeMoreActivity::class.java)
        startActivity(intent)

    }
}