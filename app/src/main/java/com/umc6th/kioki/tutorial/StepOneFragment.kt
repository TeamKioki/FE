package com.umc6th.kioki.tutorial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentStepOneBinding
import com.umc6th.kioki.utils.TextPrefs

class StepOneFragment : Fragment() {
    private lateinit var binding: FragmentStepOneBinding
    private val viewModel: TutorialViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        viewModel.setScreen(TutorialScreen.StepOne)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStepOneBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (TextPrefs(requireContext()).getTextSize()) {
                menuTitle.textSize = 20f
                    menuPrice.textSize = 16f
                orderPrice.textSize = 16f
                textView29.textSize = 16f
                textView30.textSize = 16f
                textView31.textSize = 16f
                textView32.textSize = 16f
                textView33.textSize = 16f
                discountPrice.textSize = 16f
                textView34.textSize = 22f
                totalPaymentPrice.textSize = 22f
            }
        }
        viewModel.setScreen(TutorialScreen.StepOne)

        binding.payButton.setOnClickListener {
            findNavController().navigate(R.id.action_stepOneFragment_to_stepTwoFragment)
        }
    }
}