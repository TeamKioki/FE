package com.umc6th.kioki

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// FragmentStateAdapter를 사용해서 표시할 프래그먼트를 제공해준다
class PagerFragmentStateAdapter(activity:FragmentActivity) : FragmentStateAdapter(activity) {
    private val PAGE_SIZE = 3 // 반환할 페이지 수 ( 통신 시 바꿔야겠지 )

    override fun getItemCount(): Int {
        // return (UserLists.users.size + PAGE_SIZE - 1) / PAGE_SIZE // 전체 유저 수를 페이지 크기로 나눔

        return PAGE_SIZE
    }

    override fun createFragment(position: Int): Fragment {
         return HomeUsersFragment.newInstance(position)

//        val start = position * PAGE_SIZE
//        val end = minOf(start + PAGE_SIZE, UserLists.users.size)
//        val userList = UserLists.users.subList(start, end)
//        return HomeUsersFragment.newInstance(userList)
    }

}