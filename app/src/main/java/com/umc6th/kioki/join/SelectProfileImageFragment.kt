package com.umc6th.kioki.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.umc6th.myapplication.R
import com.umc6th.myapplication.databinding.FragmentSelectProfileImageBinding

class SelectProfileImageFragment : Fragment() {

    private lateinit var binding: FragmentSelectProfileImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectProfileImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectCharacter()

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_selectProfileImageFragment_to_inputMemberIntroduceFragment)

        }
    }

    private fun selectCharacter() {
        binding.apply {
            character1.setOnClickListener {
                binding.selectProfileImageButton.setImageResource(R.drawable.profile1png)
            }

            character2.setOnClickListener {
                binding.selectProfileImageButton.setImageResource(R.drawable.profile2)

            }

            character3.setOnClickListener {
                binding.selectProfileImageButton.setImageResource(R.drawable.profile3)

            }

            character4.setOnClickListener {
                binding.selectProfileImageButton.setImageResource(R.drawable.profile4)

            }
        }
    }
}