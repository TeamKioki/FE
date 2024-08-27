package com.umc6th.kioki.group

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.R
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.ActivityGroupHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GroupHomeActivity: AppCompatActivity(), OnItemClickListener, OnGroupMemberChangeListener {
    private lateinit var binding: ActivityGroupHomeBinding
    lateinit var groupList: MutableList<MemberEntity>
    private lateinit var groupListAdapter: GroupRvAdapter
    private lateinit var apiService: GroupRetrofitInterface
    val accessToken =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInBob25lIjoiMDEwODI0NzMwMTAiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzI0MzE5MjM5LCJleHAiOjE3MjY5MTEyMzl9.Zwz108s5qKDBo02nm16H_Ma_P0CnkUybG66XbkOk9_A"

    private var currentTheme = R.style.Theme_App_Medium // 텍스트뷰 기본 크기
    private lateinit var pref : DefaultPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃 설정
        binding = ActivityGroupHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 연결할 api 설정
        apiService =
            RetrofitClient.create(GroupRetrofitInterface::class.java)

        // API 호출
        //fetchMembers(accessToken)  // 멤버 목록 가져오기

        // 뒤로가기 버튼 이벤트
        val backBtn = binding.groupHeaderNavBackIv
        backBtn.setOnClickListener {
            finish()
        }

//        groupList = mutableListOf()
//        groupList.addAll(MemberLists.members)
        groupList = MemberLists.members.filter { it.isGroupMember == true }.toMutableList()

        Log.d("멤버목록", "그룹친구 목록: ${groupList}")

        // 리싸이클러뷰
        val rv = binding.groupRecyclerview
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // rv에 어댑터 연결
        groupListAdapter = GroupRvAdapter(groupList, this, apiService, accessToken)
        rv.adapter = groupListAdapter

        // DiffUtil 적용 후 데이터 추가
        groupListAdapter.differ.submitList(groupList)

        // 삭제 편집 버튼 누르면 모든 리스트 왼쪽에 삭제 버튼 뜨게 하기
        binding.groupDeleteBtnIv.setOnClickListener {
            groupListAdapter.toggleDeleteButtonVisibility()
        }

        // 검색 기능
        binding.groupSearchIcon.setOnClickListener {
            val groupNickname = binding.groupSearchEt.text.toString()
            if (groupNickname.isEmpty()) {
                fetchMembers(accessToken) // 검색어가 없으면 전체 목록을 다시 불러오기
            } else {
                searchMembers(accessToken, groupNickname)
            }
        }

        // 글자 크기
        pref = DefaultPreferenceManager(this)
        val textSize = pref.getTextSize()
        currentTheme = getAppTheme(textSize)
        setTheme(currentTheme)
    }

    override fun onResume() {
        super.onResume()
        Log.d("확인", "resume")
        onGroupMemberChanged()

        // 글자 크기에 따라 테마를 다시 설정
        val textSize = pref.getTextSize()
        val newTheme = getAppTheme(textSize)

        if (currentTheme != newTheme) {
            currentTheme = newTheme
            recreate()  // Activity 재생성하여 새로운 테마 적용
        }
        //groupListAdapter.differ.submitList(groupList)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("확인", "restart")
        //fetchMembers(accessToken)
        //groupListAdapter.differ.submitList(groupList)

    }
    fun updateMemberData(memberId: Int, newMemberName: String, newNoteTitle: String, newNoteText: String) {
        // memberId에 해당하는 아이템을 찾아 업데이트
        val member = groupList.find { it.memberId == memberId }
        member?.let {
            it.nickname = newMemberName
            it.noteTitle = newNoteTitle
            it.noteText = newNoteText

            // RecyclerView 갱신
            groupListAdapter.notifyDataSetChanged()
        }
    }

    private fun getAppTheme(textSize: Int) =
        when (textSize) {
            0 -> R.style.Theme_App_Small
            1 -> R.style.Theme_App_Medium
            2 -> R.style.Theme_App_Large
            else -> R.style.Theme_App_Medium
        }

    // API
    private fun fetchMembers(token: String) {
        apiService.getMembers("Bearer $token").enqueue(object :
            Callback<GroupMembersResponse> {
            override fun onResponse(
                call: Call<GroupMembersResponse>,
                response: Response<GroupMembersResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val result = response.body()
                    Log.d("통신", "GroupMembers Response: $result")
                    // 멤버 목록 처리
                    val groupMembers = result?.result
                    if (groupMembers != null && groupMembers.isNotEmpty()) {
                        groupList.clear()
                        groupList.addAll(groupMembers.map { member ->
                            MemberEntity(
                                memberId = member.memberId,
                                userId = member.userId,
                                profilePictureUrl = R.drawable.ic_home_user_character1,
                                nickname = member.nickname,
                                noteTitle = member.noteTitle,
                                noteText = member.noteText,
                            )
                        })
                        Log.d("멤버목록", groupList.toString())

                        groupListAdapter.differ.submitList(groupList)
                    }
                } else {
                    Log.e(
                        "통신",
                        "GroupMembers Response 실패: ${response.code()} - ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<GroupMembersResponse>, t: Throwable) {
                Log.e("통신", "통신 실패: ${t.message}", t)
            }
        })
    }
    private fun searchMembers(token: String, nickname: String) {
        apiService.searchMembers("Bearer $token", nickname).enqueue(object :
            Callback<GroupMembersResponse> {
            override fun onResponse(
                call: Call<GroupMembersResponse>,
                response: Response<GroupMembersResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val result = response.body()
                    Log.d("통신", "GroupMembers Response: $result")
                    // 멤버 목록 처리
                    val groupMembers = result?.result
                    groupList.clear()
                    if (groupMembers != null && groupMembers.isNotEmpty()) {
                        groupList.addAll(groupMembers.map { member ->
                            MemberEntity(
                                memberId = member.memberId,
                                userId = member.userId,
                                profilePictureUrl = R.drawable.ic_home_user_character1,
                                nickname = member.nickname,
                                noteTitle = member.noteTitle,
                                noteText = member.noteText,
                            )
                        })
                    } else {
                        Log.d("통신", "검색 결과가 없습니다.")
                    }
                    groupListAdapter.differ.submitList(groupList)
                } else {
                    Log.e(
                        "통신",
                        "GroupMembers Response 실패: ${response.code()} - ${response.message()}"
                    )
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
        bundle.putInt("MemberId", member.memberId!!)
        bundle.putString("MemberName", member.nickname)
        bundle.putString("MemberNoteTitle", member.noteTitle)
        bundle.putString("MemberNoteText", member.noteText)
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "GroupHomeEditFragmentDialog")
    }

    // 그룹추가 버튼 클릭 시 GroupHomeMoreActivity로 넘어가는 이벤트
    override fun onAddButtonClick() {
        val intent = Intent(this, GroupHomeMoreActivity::class.java)
        startActivity(intent)

    }

    override fun onGroupMemberChanged() {
        groupList = MemberLists.members.filter { it.isGroupMember == true }.toMutableList()
        groupListAdapter.differ.submitList(groupList)
        groupListAdapter.notifyDataSetChanged()
    }
}