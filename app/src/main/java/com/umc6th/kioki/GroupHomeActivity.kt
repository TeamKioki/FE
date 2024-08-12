package com.umc6th.kioki

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.databinding.ActivityGroupHomeBinding


class GroupHomeActivity: Activity() {
    private lateinit var binding: ActivityGroupHomeBinding
    private lateinit var groupList: List<Group>
    private lateinit var groupListAdapter: GroupListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃 설정
        binding = ActivityGroupHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("그룹홈", "onCreate called")

        val rv = binding.groupRecyclerview
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // rv에 어댑터 연결
        groupList = GroupLists.groups
        groupListAdapter = GroupListAdapter(groupList)
        rv.adapter = groupListAdapter

        // DiffUtil 적용 후 데이터 추가
        groupListAdapter.differ.submitList(groupList)

        // 삭제 편집 버튼 누르면 모든 리스트 왼쪽에 삭제 버튼 뜨게 하기
        binding.groupDeleteBtnIv.setOnClickListener {
            groupListAdapter.toggleDeleteButtonVisibility()
        }
    }

}