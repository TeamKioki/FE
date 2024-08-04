package com.umc6th.kioki.join

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.umc6th.myapplication.R
import com.umc6th.myapplication.databinding.FragmentInputMemberInfoBinding

class InputMemberInfoFragment : Fragment() {
    private lateinit var binding: FragmentInputMemberInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputMemberInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            inputName.addTextChangedListener(nameTextWatcher)
            inputIdentifyNumber.addTextChangedListener(identityNumberTextWatcher)
        }

        binding.nextButton.setOnClickListener {
            if (validateInput()) {
                findNavController().navigate(R.id.action_inputMemberInfoFragment_to_selectProfileImageFragment)
            }
        }
    }

    private fun validateInput(): Boolean {
        var validateFlag = true

        val name = binding.inputName.text.toString()
        val birthDay = binding.inputBirthDay.text.toString()
        val identifyNumber = binding.inputIdentifyNumber.text.toString()

        if (name.isEmpty()) {
            binding.emptyNameText.visibility = View.VISIBLE
            binding.nameIndicator.setBackgroundColor(Color.RED)
            validateFlag = false
        }

        if (identifyNumber.isEmpty() || birthDay.isEmpty()) {
            binding.emptyIdentifyNumberText.visibility = View.VISIBLE
            binding.identifyNumberIndicator.setBackgroundColor(Color.RED)
            validateFlag = false
        }
        return validateFlag
    }

    private val nameTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            if (s.toString().isNotBlank()) {
                binding.apply {
                    nameIndicator.setBackgroundColor(resources.getColor(R.color.hint))
                    emptyNameText.visibility = View.INVISIBLE
                }
            }
        }
    }

    private val identityNumberTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            if (s.toString().isNotBlank()) {
                binding.apply {
                    identifyNumberIndicator.setBackgroundColor(resources.getColor(R.color.hint))
                    emptyIdentifyNumberText.visibility = View.INVISIBLE
                }
            }
        }
    }

    companion object {
        private const val TAG = "InputMemberInfoFragment"
    }
}