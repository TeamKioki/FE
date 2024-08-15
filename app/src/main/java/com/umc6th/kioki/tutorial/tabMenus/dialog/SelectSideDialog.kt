package com.umc6th.kioki.tutorial.tabMenus.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.umc6th.kioki.tutorial.TutorialViewModel
import com.umc6th.kioki.tutorial.adapter.SideAdapter
import com.umc6th.kioki.utils.dialogFragmentResize
import com.umc6th.kioki.databinding.FragmentSelectSideDialogBinding

class SelectSideDialog : DialogFragment() {
    private lateinit var binding: FragmentSelectSideDialogBinding
    private val sideAdapter: SideAdapter by lazy {
        SideAdapter {
            viewModel.selectSideMenu(it)
        }
    }
    private val viewModel by activityViewModels<TutorialViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 0.95f, 0.65f)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectSideDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSideMenus()
        observeTotalPrice()
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.completeButton.setOnClickListener {
            SelectSideDialog().show(parentFragmentManager, "SelectDrinkDialog")
            dismiss()
        }
        binding.sideRv.apply {
            adapter = sideAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
    }

    private fun observeSideMenus() {
        viewModel.sideMenus.observe(viewLifecycleOwner) {
            sideAdapter.setSideList(it)
        }
    }

    private fun observeTotalPrice() {
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.totalPrice.text = "${it}원"
        }
    }
}