package com.umc6th.kioki

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.databinding.ActivityGroupHomeBinding


class GroupHomeActivity: AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityGroupHomeBinding
    private var groupList: MutableList<MemberEntity> = MemberLists.groups
    private lateinit var groupListAdapter: GroupRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃 설정
        binding = ActivityGroupHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("그룹홈", "onCreate called")

        // 뒤로가기 버튼 이벤트
        val backBtn = binding.groupHeaderNavBackIv
        backBtn.setOnClickListener {
            finish()
        }

        // 리싸이클러뷰
        val rv = binding.groupRecyclerview
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // rv에 어댑터 연결
        groupListAdapter = GroupRvAdapter(groupList, this)
        rv.adapter = groupListAdapter

        // DiffUtil 적용 후 데이터 추가
        //groupListAdapter.differ.submitList(groupList)

        // 삭제 편집 버튼 누르면 모든 리스트 왼쪽에 삭제 버튼 뜨게 하기
        binding.groupDeleteBtnIv.setOnClickListener {
            groupListAdapter.toggleDeleteButtonVisibility()
        }

        // 검색 기능
        binding.groupSearchEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                var searchText:String = binding.groupSearchEt.text.toString()
                // 필터링된 리스트 생성
                val filteredList = if (searchText.isEmpty()) {
                    MemberLists.groups // 아무것도 입력되지 않을 때는 모든 데이터를 사용
                } else {
                    MemberLists.groups.filter {
                        it.memberName!!.contains(searchText, ignoreCase = true)
                    }
                }

                // 필터링된 결과를 어댑터에 전달
                //groupListAdapter.differ.submitList(filteredList)


            }

        })
    }

    // 아이템 클릭시
    override fun onItemClick(member: GroupMembersResult) {
        val dialog = GroupHomeEditFragmentDialog()
        var bundle = Bundle() // 다이얼로그에 전달할 Bundle 생성
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


}