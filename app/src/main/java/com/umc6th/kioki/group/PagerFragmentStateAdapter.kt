package com.umc6th.kioki.group

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// FragmentStateAdapter를 사용해서 표시할 프래그먼트를 제공해준다
class PagerFragmentStateAdapter(
    activity:FragmentActivity,
    //private var membersList: List<GroupMember> = emptyList()
    private var membersList: List<MemberEntity> = emptyList()
    ) : FragmentStateAdapter(activity) {
    var pageCount = 1

    override fun getItemCount(): Int {
        //return 2
        //return if (membersList.size > 3) pageCount + 1 else 1
        return if (membersList.size <= 3) 1 else (membersList.size - 3 + 3) / 4 + 1
    }

    override fun createFragment(position: Int): Fragment {
//        val member = membersList[position]
//        return HomeUsersFragment.newInstance(member)
        //return HomeUsersFragment.newInstance(position)
        //return HomeUsersFragment.newInstance(membersList[position])

//        val startIndex = position * 4
//        val endIndex = minOf(startIndex + 4, membersList.size)
//        val pageMembers = membersList.subList(startIndex, endIndex)
//
//        return HomeUsersFragment.newInstance(startIndex, endIndex)
        val fragment = HomeUsersFragment()
        if (position == 0) {
            // 첫 번째 페이지: 3개의 아이템만 보여줌
            val firstPageMembers = membersList.subList(0, minOf(3, membersList.size))
            fragment.setMembers(firstPageMembers)
        } else {
            // 나머지 페이지: 4개의 아이템씩 보여줌
            val startIndex = 3 + (position - 1) * 4
            val endIndex = minOf(startIndex + 4, membersList.size)
            val pageMembers = membersList.subList(startIndex, endIndex)
            fragment.setMembers(pageMembers)
        }
        return fragment
    }

    fun updateMembers(newMembers: List<MemberEntity>) {
        //membersList = newMembers
        membersList = newMembers.filter { it.isGroupMember == true }
        notifyDataSetChanged()
    }

//    fun updateMembers(newMembers: List<GroupMember>) {
//        //membersList = newMembers
//        membersList = newMembers.filter { it.isGroupMember }
//        notifyDataSetChanged()
//    }
}