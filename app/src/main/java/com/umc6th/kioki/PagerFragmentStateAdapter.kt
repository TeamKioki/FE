package com.umc6th.kioki

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// FragmentStateAdapter를 사용해서 표시할 프래그먼트를 제공해준다
class PagerFragmentStateAdapter(activity:FragmentActivity) : FragmentStateAdapter(activity) {
    private val PAGE_SIZE = 3 // 반환할 페이지 수 ( 통신 시 바꿔야겠지 )

    override fun getItemCount(): Int {
        return PAGE_SIZE
    }

    override fun createFragment(position: Int): Fragment {
         return HomeUsersFragment.newInstance(position)

    }

}