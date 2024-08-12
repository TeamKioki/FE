package com.umc6th.kioki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.myapplication.Group
import com.umc6th.myapplication.GroupLists
import com.umc6th.myapplication.databinding.FragmentHomeGroupBinding

class HomeGroupFragment:Fragment() {
    private lateinit var binding: FragmentHomeGroupBinding
    private lateinit var groupList: List<Group>
    private lateinit var groupListAdapter: GroupListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHomeGroupBinding.inflate(layoutInflater)
        val rv = binding.groupRecyclerview
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        // rv에 어댑터 연결
        groupList = GroupLists.groups
        groupListAdapter = GroupListAdapter(groupList)
        rv.adapter = groupListAdapter

        // DiffUtil 적용 후 데이터 추가
        groupListAdapter.differ.submitList(groupList)

        return binding.root
    }
}