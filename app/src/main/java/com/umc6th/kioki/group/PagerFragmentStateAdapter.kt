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

    override fun getItemCount(): Int {
        //return 2
        return if (membersList.size > 3) membersList.size + 1 else 1
    }

    override fun createFragment(position: Int): Fragment {
        val member = membersList[position]
        return HomeUsersFragment.newInstance(member)
        //return HomeUsersFragment.newInstance(position)
        //return HomeUsersFragment.newInstance(membersList[position])
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