package com.umc6th.kioki.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentInputMemberIntroduceBinding

class InputMemberIntroduceFragment : Fragment() {
    private lateinit var binding: FragmentInputMemberIntroduceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputMemberIntroduceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_inputMemberIntroduceFragment_to_selectKioskIssueFragment)
        }
    }
}