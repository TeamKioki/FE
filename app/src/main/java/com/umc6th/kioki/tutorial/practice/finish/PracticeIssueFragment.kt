package com.umc6th.kioki.tutorial.practice.finish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentPracticeIssueBinding
import com.umc6th.kioki.join.KioskIssue
import com.umc6th.kioki.join.adapter.KioskIssueAdapter

class PracticeIssueFragment : Fragment() {
    private lateinit var binding: FragmentPracticeIssueBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPracticeIssueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appCompatButton3.setOnClickListener {
            findNavController().navigate(R.id.action_practiceIssueFragment_to_finishAndRetryFragment)
        }
        val flexboxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
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
        issueAdapter.submitList(issues)
    }

}