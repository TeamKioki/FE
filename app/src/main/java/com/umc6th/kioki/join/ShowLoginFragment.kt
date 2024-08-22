package com.umc6th.kioki.join

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentPermissionBottomSheetBinding
import com.umc6th.kioki.databinding.FragmentShowLoginBinding
import com.umc6th.kioki.login.LoginActivity

class ShowLoginFragment : Fragment() {
    private lateinit var binding: FragmentShowLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }
}