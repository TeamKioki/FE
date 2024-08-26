package com.umc6th.kioki.group

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// FragmentStateAdapter를 사용해서 표시할 프래그먼트를 제공해준다
class PagerFragmentStateAdapter(
    activity:FragmentActivity,
    private var membersList: List<GroupMember> = emptyList()
    ) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return HomeUsersFragment.newInstance(position)
    }

    fun updateMembers(newMembers: List<GroupMember>) {
        membersList = newMembers
        notifyDataSetChanged()
    }

}