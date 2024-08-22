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
import java.text.NumberFormat
import java.util.Locale

class StepOneFragment : Fragment() {
    private lateinit var binding: FragmentStepOneBinding
    private val viewModel: TutorialViewModel by activityViewModels()
    private val price = 10800
    private var orderPrice = 0
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
        viewModel.setOrderPrice(10800)
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

        binding.apply {
            menuTitle.text = viewModel.selectedMenuItem.value?.name
        }

        binding.payButton.setOnClickListener {
            findNavController().navigate(R.id.action_stepOneFragment_to_stepTwoFragment)
        }

        binding.plusButton.setOnClickListener {
            val plusCount = binding.textView30.text.toString().toInt() + 1
            val currentPrice = (price * plusCount).toInt()
            val decimalPrice = NumberFormat.getNumberInstance(
                Locale.KOREA).format(currentPrice) + "원"
            binding.totalPrice.text = decimalPrice
            binding.menuPrice.text = decimalPrice
            binding.orderPrice.text = decimalPrice
            binding.totalPaymentPrice.text = decimalPrice
            binding.textView30.text = plusCount.toString()
            orderPrice = currentPrice
            viewModel.setOrderPrice(orderPrice)
        }

        binding.minusButton.setOnClickListener {
            val currentCount = binding.textView30.text.toString().toInt()
            if (currentCount > 1) {
                val minusCount = currentCount - 1
                val currentPrice = (price * minusCount).toInt()
                val decimalPrice = NumberFormat.getNumberInstance(
                    Locale.KOREA).format(currentPrice) + "원"
                binding.totalPrice.text = decimalPrice.toString()
                binding.menuPrice.text = decimalPrice.toString()
                binding.orderPrice.text = decimalPrice.toString()
                binding.totalPaymentPrice.text = decimalPrice.toString()
                binding.textView30.text = minusCount.toString()
                orderPrice = currentPrice
                viewModel.setOrderPrice(orderPrice)

            }
        }

    }
}