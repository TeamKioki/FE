package com.umc6th.kioki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.databinding.ActivityGroupHomeMoreBinding

class GroupHomeMoreActivity:AppCompatActivity(), OnItemClickListener {
    lateinit var binding:ActivityGroupHomeMoreBinding // 연결할 xml 파일
    private lateinit var groupList: MutableList<MemberEntity> // 그룹 리스트
    private lateinit var groupMoreListAdapter: GroupMoreRvAdapter // 어댑터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃 설정
        binding = ActivityGroupHomeMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뒤로가기 버튼 이벤트
        val backBtn = binding.groupMoreBackBtnIv
        backBtn.setOnClickListener {
            finish()
        }

        // 리싸이클러뷰를 어댑터와 연결
        val rv = binding.groupMoreRecyclerview
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        groupList = MemberLists.groups // 그룹리스트 초기화
        groupMoreListAdapter = GroupMoreRvAdapter(groupList, this) // 어댑터 초기화
        rv.adapter = groupMoreListAdapter // 어댑터와 연결

        // DiffUtil 적용 후 데이터 추가
        groupMoreListAdapter.differ.submitList(groupList)

    }

    override fun onItemClick(member: GroupMembersResult) {
//        val dialog = GroupHomeMoreAddFragmentDialog().show(
//            supportFragmentManager, "GroupHomeMoreAddFragmentDialog"
//        )
        val dialog = GroupHomeMoreAddFragmentDialog()
        var bundle = Bundle() // 다이얼로그에 전달할 Bundle 생성
        bundle.putInt("MemberImg", member.memberImg!!)
        bundle.putString("MemberName", member.memberName)
        bundle.putString("MemberNoteTitle", member.memberNoteTitle)
        bundle.putString("MemberNoteText", member.memberNoteText)
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "GroupHomeMoreAddFragmentDialog")

    }

    override fun onAddButtonClick() {
        return
    }
}