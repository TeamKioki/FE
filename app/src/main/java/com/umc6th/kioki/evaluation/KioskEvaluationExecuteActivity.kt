package com.umc6th.kioki.evaluation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityKioskEvaluationBinding
import com.umc6th.kioki.databinding.ActivityKioskEvaluationExecuteBinding
import com.umc6th.kioki.join.KioskIssue
import com.umc6th.kioki.join.adapter.KioskIssueAdapter

class KioskEvaluationExecuteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKioskEvaluationExecuteBinding
    private val issues = mutableListOf(
        KioskIssue("글씨가 작아서"),
        KioskIssue("사용법을 배웠지만 까먹어서"),
        KioskIssue("디자인이 달라서"),
        KioskIssue("메뉴를 찾기 힘들어서"),
        KioskIssue("사용해 본 적이 없어서"),
        KioskIssue("뒷 사람이 부담되어서"),
        KioskIssue("처음 가보는 가게여서"),
        KioskIssue("소리가 안나와서"),
    )
    private val goods = mutableListOf(
        KioskIssue("알아보기 쉬워서"),
        KioskIssue("사용법이 간단해서"),
        KioskIssue("익숙한 디자인이어서"),
        KioskIssue("결제가 간편해서"),
        KioskIssue("매장 내 키오스크가 많아서"),
    )

    private val issueAdapter: KioskIssueAdapter by lazy {
        KioskIssueAdapter { _, index ->
            val update = issues.mapIndexed {
                    i, kioskIssue ->
                if (i == index) {
                    kioskIssue.copy(
                        isSelected = !kioskIssue.isSelected
                    )
                } else {
                    kioskIssue
                }
            }
            issueAdapter.submitList(update)
        }
    }

    private val goodsAdapter: KioskIssueAdapter by lazy {
        KioskIssueAdapter { _, index ->
            val update = goods.mapIndexed {
                    i, kioskIssue ->
                if (i == index) {
                    kioskIssue.copy(
                        isSelected = !kioskIssue.isSelected
                    )
                } else {
                    kioskIssue
                }
            }
            goodsAdapter.submitList(update)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kiosk_evaluation_execute)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.evaluationButton.setOnClickListener {
            startActivity(Intent(this, KioskEvaluationFinishActivity::class.java))
            finish()
        }

        val flexboxLayoutManager = FlexboxLayoutManager(this).apply {
            //줄바꿈 설정
            flexWrap = FlexWrap.WRAP
            // item 정렬 기본 축
            flexDirection = FlexDirection.ROW
            // 정렬 기준
            justifyContent = JustifyContent.FLEX_START
        }
        val flexboxLayoutManager2 = FlexboxLayoutManager(this).apply {
            //줄바꿈 설정
            flexWrap = FlexWrap.WRAP
            // item 정렬 기본 축
            flexDirection = FlexDirection.ROW
            // 정렬 기준
            justifyContent = JustifyContent.FLEX_START
        }
        binding.issueRv.apply {
            adapter = issueAdapter
            layoutManager = flexboxLayoutManager
        }
        binding.goodRv.apply {
            adapter = goodsAdapter
            layoutManager = flexboxLayoutManager2
        }
        issueAdapter.submitList(issues)
        goodsAdapter.submitList(goods)
    }
}