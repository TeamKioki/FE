package com.umc6th.kioki.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentStepTwoBinding
import com.umc6th.kioki.utils.TextPrefs

class StepTwoFragment : Fragment() {
    private lateinit var binding: FragmentStepTwoBinding
    private val viewModel: TutorialViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        viewModel.setScreen(TutorialScreen.StepTwo)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStepTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (TextPrefs(requireContext()).getTextSize()) {
            binding.apply {
                text1.textSize = 16f
                text2.textSize = 16f
                text3.textSize = 16f
                text4.textSize = 16f
                orderPrice.textSize = 16f
                textView32.textSize = 16f
                discountPrice.textSize = 16f
                textView33.textSize = 16f
                textView34.textSize = 22f
                totalPaymentPrice.textSize = 22f

            }
        }
        viewModel.setScreen(TutorialScreen.StepTwo)
    }
}