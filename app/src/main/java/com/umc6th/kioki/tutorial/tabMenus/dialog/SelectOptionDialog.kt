package com.umc6th.kioki.tutorial.tabMenus.dialog

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.umc6th.kioki.tutorial.MenuOption
import com.umc6th.kioki.tutorial.TutorialViewModel
import com.umc6th.kioki.utils.dialogFragmentResize
import com.umc6th.kioki.databinding.FragmentSelectOptionDialogBinding
import com.umc6th.kioki.tutorial.tutorial.OptionTutorialActivity

class SelectOptionDialog : DialogFragment() {
    private val viewModel: TutorialViewModel by activityViewModels()
    private lateinit var binding: FragmentSelectOptionDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 0.95f, 0.7f)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        // 다이얼로그의 배경을 투명하게 설정
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 다이얼로그 외부를 터치 가능하게 설정
        dialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )

        // 다이얼로그 외부를 클릭해도 다이얼로그가 닫히지 않도록 설정
        dialog.setCanceledOnTouchOutside(false)

        return dialog
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
        binding.exitItem.setOnClickListener {
            dismiss()
            requireActivity().finish()
        }
        binding.tutorialItem.setOnClickListener {
            startActivity(Intent(requireContext(), OptionTutorialActivity::class.java))
        }
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