package com.umc6th.kioki.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc6th.kioki.MainActivity
import com.umc6th.kioki.join.JoinActivity
import com.umc6th.kioki.login.LoginActivity
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentLastBinding

class LastFragment : Fragment() {
    private lateinit var binding: FragmentLastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startTextView.setOnClickListener {
            startActivity(Intent(requireContext(), JoinActivity::class.java))
            requireActivity().finish()
        }
    }
}