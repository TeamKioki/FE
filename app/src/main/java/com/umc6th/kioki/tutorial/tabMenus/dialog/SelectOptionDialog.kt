package com.umc6th.kioki.tutorial.tabMenus.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.umc6th.kioki.tutorial.MenuOption
import com.umc6th.kioki.tutorial.TutorialViewModel
import com.umc6th.kioki.utils.dialogFragmentResize
import com.umc6th.myapplication.databinding.FragmentSelectOptionDialogBinding

class SelectOptionDialog : DialogFragment() {
    private val viewModel: TutorialViewModel by activityViewModels()
    private lateinit var binding: FragmentSelectOptionDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 0.95f, 0.6f)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectOptionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSelectedMenu()

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.apply {
            largeCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    normalCheckBox.isChecked = false
                    singleCheckBox.isChecked = false
                    viewModel.setCheckedOption(MenuOption.LARGE_SET)
                }
            }
            normalCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    largeCheckBox.isChecked = false
                    singleCheckBox.isChecked = false
                    viewModel.setCheckedOption(MenuOption.SET)
                }
            }
            singleCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    largeCheckBox.isChecked = false
                    normalCheckBox.isChecked = false
                    viewModel.setCheckedOption(MenuOption.SINGLE)
                }
            }
        }
        binding.confirmButton.setOnClickListener {
            if (viewModel.selectedOption.value != MenuOption.SINGLE) {
                SelectSideDialog().show(parentFragmentManager, "SelectSideDialog")
                dismiss()
            }
            dismiss()
        }
    }

    private fun observeSelectedMenu() {
        viewModel.selectedMenuItem.observe(viewLifecycleOwner) { item ->
            val menuName = item.name
            binding.apply {
                largeTitle.text = "$menuName 라지세트"
                largeDescription.text = "$menuName+프렌치프라이(L)+콜라(L)"
                largePrice.text = "11,500원"
                normalTitle.text = "$menuName 세트"
                normalDescription.text = "$menuName+프렌치프라이(R)+콜라(R)"
                normalPrice.text = "10,800원"
                singleTitle.text = "$menuName"
                singleDescription.text = "단품"
                singlePrice.text = "9,300원"
            }
        }
    }
}