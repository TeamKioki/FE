package com.umc6th.kioki.join

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentPermissionBottomSheetBinding

class PermissionBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPermissionBottomSheetBinding
    private val viewModel: JoinViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPermissionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.permission1.text = Html.fromHtml("테스트 " + "<font color=\"#2a7de2\"><u>파란색</u></font>" + "입니다 ")
        binding.apply {
            permission1.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            permission2.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            permission3.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            permission4.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        }

        updateCheckAllState()

        // Set listeners for each CheckBox
        binding.check1.setOnCheckedChangeListener { _, _ -> updateCheckAllState() }
        binding.check2.setOnCheckedChangeListener { _, _ -> updateCheckAllState() }
        binding.check3.setOnCheckedChangeListener { _, _ -> updateCheckAllState() }
        binding.check4.setOnCheckedChangeListener { _, _ -> updateCheckAllState() }

        binding.checkAll.setOnClickListener {
            if (binding.checkAll.isChecked) {
                binding.check1.isChecked = true
                binding.check2.isChecked = true
                binding.check3.isChecked = true
                binding.check4.isChecked = true
            } else {
                binding.check1.isChecked = false
                binding.check2.isChecked = false
                binding.check3.isChecked = false
                binding.check4.isChecked = false
            }
        }

        binding.joinButton.setOnClickListener {
            if (binding.check1.isChecked && binding.check2.isChecked && binding.check3.isChecked && binding.check4.isChecked) {
//                viewModel.executeJoin()
                findNavController().navigate(R.id.action_requirePermissionFragment_to_showLoginFragment)
                dismiss()
            } else {
                Toast.makeText(requireContext(), "모든 항목에 동의해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateCheckAllState() {
        // Update checkAll CheckBox based on the state of other CheckBoxes
        binding.checkAll.isChecked = binding.check1.isChecked &&
                binding.check2.isChecked &&
                binding.check3.isChecked &&
                binding.check4.isChecked
    }
}