package com.umc6th.kioki.join

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.umc6th.kioki.join.adapter.KioskIssueAdapter
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentSelectKioskIssueBinding
import kotlinx.coroutines.launch

class SelectKioskIssueFragment : Fragment() {

    private lateinit var binding: FragmentSelectKioskIssueBinding
    private val viewModel by viewModels<JoinViewModel>()

    private val kioskIssueAdapter by lazy {
        KioskIssueAdapter { _, index->
            viewModel.selectKioskIssue(index)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectKioskIssueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeIssues()


        val flexboxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            //줄바꿈 설정
            flexWrap = FlexWrap.WRAP
            // item 정렬 기본 축
            flexDirection = FlexDirection.ROW
            // 정렬 기준
            justifyContent = JustifyContent.FLEX_START
        }

        binding.issueRecyclerView.apply {
            adapter = kioskIssueAdapter
            layoutManager = flexboxLayoutManager
        }

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_selectKioskIssueFragment_to_requirePermissionFragment)
        }
    }

    private fun observeIssues() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.kioskIssues.collect {
                    kioskIssueAdapter.submitList(it)
                }
            }
        }
    }
}