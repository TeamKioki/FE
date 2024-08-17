package com.umc6th.kioki.tutorial.practice.finish

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentRetryBinding
import com.umc6th.kioki.tutorial.BurgerKingRealActivity

class RetryFragment : Fragment() {
    private lateinit var binding: FragmentRetryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRetryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.retryButton.setOnClickListener {
            startActivity(Intent(requireContext(), BurgerKingRealActivity::class.java).apply {
                putExtra("isPracticeMode", true)
            })
            requireActivity().finishAffinity()
        }
        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_retryFragment_to_evaluationFragment)
        }
    }
}