package com.umc6th.kioki.group

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.KioskSettingFragmentDialog
import com.umc6th.kioki.databinding.ActivityNavAccountEditBinding
import com.umc6th.kioki.evaluation.KioskEvaluationActivity

class NavAccountEditActivity : AppCompatActivity() {
    lateinit var binding : ActivityNavAccountEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavAccountEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kiostEvaluationBtn.setOnClickListener {
            startActivity(Intent(this, KioskEvaluationActivity::class.java))
        }
        binding.groupHeaderNavBackIv.setOnClickListener {
            finish()
        }

        // 친구 목록 버튼 이벤트
        binding.navGroupListBtnIv.setOnClickListener {
            val intent = Intent(this, GroupHomeActivity::class.java)
            startActivity(intent)
        }

        // 키오스크 평가 리싸이클러뷰
        binding.accountEditKioskReviewRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.accountEditKioskReviewRv.adapter = ReviewRvAdapter(DummyLists.reivews)

        // 친구 목록 리싸이클러뷰
        binding.accountEditGroupListRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val filteredMembers = MemberLists.members.filter { it.isGroupMember == true }
        binding.accountEditGroupListRv.adapter = AccountEditGroupRvAdapter(filteredMembers)


    }
}