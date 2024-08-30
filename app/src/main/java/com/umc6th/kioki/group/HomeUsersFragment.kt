package com.umc6th.kioki.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.FragmentHomeUsersBinding

class HomeUsersFragment : Fragment() {
    private lateinit var binding: FragmentHomeUsersBinding
    private lateinit var members: List<MemberEntity>
//    private lateinit var members: List<GroupMember>
    private lateinit var apiService: GroupRetrofitInterface
    private lateinit var adapter: HomeUsersAdapter
    val accessToken =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInBob25lIjoiMDEwODI0NzMwMTAiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzI0MzE5MjM5LCJleHAiOjE3MjY5MTEyMzl9.Zwz108s5qKDBo02nm16H_Ma_P0CnkUybG66XbkOk9_A"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeUsersBinding.inflate(layoutInflater) // fragment_home_users.xml과 연결
        binding.homeRecyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        //binding.homeRecyclerview.adapter = HomeUsersAdapter(members)

        // 초기 빈 어댑터 설정
        adapter = HomeUsersAdapter(emptyList())
        binding.homeRecyclerview.adapter = adapter

        // members 리스트를 어댑터에 설정
        if (::members.isInitialized) {
            adapter.updateMembers(members)
        }
        val choice =arguments?.getInt(MEMBER_ITEM)

        // 연결할 api 설정
        apiService = RetrofitClient.create(GroupRetrofitInterface::class.java) // baseurl 뒤에 붙일 url이 있는 인터페이스 파일 연결

//        choice?.let {
//            when(choice) {
//                0 -> HomeUsersAdapter(MemberLists.members)
//                1 -> HomeUsersAdapter(MemberLists.members)
//                else -> HomeUsersAdapter(MemberLists.members)
////                1 -> fetchMembersPaged(accessToken, choice)
////                2 -> fetchMembersPaged(accessToken, choice)
////                else -> fetchMembersPaged(accessToken, choice)
//            }
//        }
        choice?.let {
            val filteredMembers = MemberLists.members.filter { it.isGroupMember == true }
            adapter.updateMembers(filteredMembers)

            //HomeUsersAdapter(filteredMembers)
        }
        arguments?.let { args ->
            val startIndex = args.getInt(START_INDEX)
            val endIndex = args.getInt(END_INDEX)
            members = MemberLists.members.subList(startIndex, endIndex)
            adapter.updateMembers(members)
        }
        return binding.root
    }
    fun setMembers(members: List<MemberEntity>) {
        this.members = members
        if (::adapter.isInitialized) {
            adapter.updateMembers(members)
        }
    }
    companion object {
        private const val MEMBER_ITEM = "member_item"
        private const val START_INDEX = "start_index"
        private const val END_INDEX = "end_index"
        @JvmStatic
        fun newInstance(member: MemberEntity) =
            HomeUsersFragment().apply {
                arguments = Bundle().apply {
                    member.memberId?.let { putInt(MEMBER_ITEM, it) }
                }
            }
//    @JvmStatic
//    fun newInstance(startIndex: Int, endIndex: Int): HomeUsersFragment {
//        return HomeUsersFragment().apply {
//            arguments = Bundle().apply {
//                putInt(START_INDEX, startIndex)
//                putInt(END_INDEX, endIndex)
//        }
//    }


    }

//    private fun fetchMembersPaged(token: String, page: Int) {
//        apiService.getMembersPaged("Bearer $token", page).enqueue(object :
//            Callback<GroupMembersPagedResponse> {
//            override fun onResponse(call: Call<GroupMembersPagedResponse>, response: Response<GroupMembersPagedResponse>) {
//                if (response.isSuccessful && response.code() == 200) {
//                    val result = response.body()
//                    Log.d("통신", "Response Paged: $result")
//                    result?.let {
//                        val members = result.result.groupMemberList
//                        //updateViewPager(members)  // 데이터를 기반으로 ViewPager를 업데이트
//                        adapter.updateMembers(members)  // 어댑터에 새 데이터 반영
//                    }
//                } else {
//                    Log.e("통신", "Response 실패: ${response.code()} - ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<GroupMembersPagedResponse>, t: Throwable) {
//                Log.e("통신", "통신 실패: ${t.message}", t)
//            }
//        })
//    }

}