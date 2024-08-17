package com.umc6th.kioki

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.FragmentHomeUsersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeUsersFragment : Fragment() {
    private lateinit var binding: FragmentHomeUsersBinding
    private lateinit var members: List<GroupMember>
    private lateinit var apiService: GroupRetrofitInterface
    private lateinit var adapter: HomeUsersAdapter

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

        val choice =arguments?.getInt(MEMBER_ITEM)
        // 연결할 api 설정
        apiService = RetrofitClient.create(GroupRetrofitInterface::class.java) // baseurl 뒤에 붙일 url이 있는 인터페이스 파일 연결

        // 서버에서 제공받은 Access Token
        val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGhvbmUiOiIwMTAxMjM0NTY3OCIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MjM3MTU1NTgsImV4cCI6MTcyNjMwNzU1OH0._TI2xGiWqvtNp9ooaf_rRo8puTA1tAZqKoAjADmKwOA"


        choice?.let {
            when(choice) {
                0 -> fetchMembersPaged(accessToken, choice)
                1 -> fetchMembersPaged(accessToken, choice)
                2 -> fetchMembersPaged(accessToken, choice)
                else -> fetchMembersPaged(accessToken, choice)
            }
        }
        return binding.root
    }

    companion object {
        private const val MEMBER_ITEM = "member_item"

        @JvmStatic
        fun newInstance(position: Int) =
            HomeUsersFragment().apply {
                arguments = Bundle().apply {
                    putInt(MEMBER_ITEM, position)
                }
            }
    }

    private fun fetchMembersPaged(token: String, page: Int) {
        apiService.getMembersPaged("Bearer $token", page).enqueue(object :
            Callback<GroupMembersPagedResponse> {
            override fun onResponse(call: Call<GroupMembersPagedResponse>, response: Response<GroupMembersPagedResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val result = response.body()
                    Log.d("통신", "Response Paged: $result")
                    result?.let {
                        val members = result.result.groupMemberList
                        //updateViewPager(members)  // 데이터를 기반으로 ViewPager를 업데이트
                        adapter.updateMembers(members)  // 어댑터에 새 데이터 반영
                    }
                } else {
                    Log.e("통신", "Response 실패: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GroupMembersPagedResponse>, t: Throwable) {
                Log.e("통신", "통신 실패: ${t.message}", t)
            }
        })
    }

}