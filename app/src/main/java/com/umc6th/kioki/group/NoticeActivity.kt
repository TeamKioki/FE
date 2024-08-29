package com.umc6th.kioki.group

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityNoticeBinding

class NoticeActivity:AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃 설정
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 뒤로 가기 버튼 설정
        binding.groupHeaderNavBackIv.setOnClickListener {
            finish() // 현재 액티비티 종료
        }

        // RecyclerView 설정
        val recyclerView = findViewById<RecyclerView>(R.id.notice_rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NoticeRvAdapter(DummyLists.notices)
    }

}