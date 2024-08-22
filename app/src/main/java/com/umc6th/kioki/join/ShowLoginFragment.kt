package com.umc6th.kioki.join

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.umc6th.kioki.MainActivity
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentShowLoginBinding
import com.umc6th.kioki.tutorial.BurgerKingRealActivity

class ShowLoginFragment : Fragment() {
    private lateinit var binding: FragmentShowLoginBinding
    private val viewModel: JoinViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            viewModel.executeJoin()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }
}