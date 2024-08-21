package com.umc6th.kioki.tutorial.tabMenus.dialog

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.umc6th.kioki.databinding.FragmentSelectDrinkDialogBinding
import com.umc6th.kioki.tutorial.TutorialViewModel
import com.umc6th.kioki.tutorial.adapter.SideAdapter
import com.umc6th.kioki.tutorial.tutorial.DrinkTutorialActivity
import com.umc6th.kioki.utils.dialogFragmentResize

class SelectDrinkDialog : DialogFragment() {
    private lateinit var binding: FragmentSelectDrinkDialogBinding
    private val viewModel: TutorialViewModel by activityViewModels()
    private val sideAdapter by lazy {
        SideAdapter {
            viewModel.selectDrinkMenu(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 0.95f, 0.8f)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectDrinkDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDrinkMenus()
        observeTotalPrice()

        binding.tutorialItem.setOnClickListener {
            startActivity(Intent(requireContext(), DrinkTutorialActivity::class.java))
        }
        binding.drinkRv.apply {
            adapter = sideAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
        binding.completeButton.setOnClickListener {
            viewModel.addTotalMenu()
            dismiss()
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.exitItem.setOnClickListener {
            dismiss()
            requireActivity().finish()
        }
    }

    private fun observeDrinkMenus() {
        viewModel.drinkMenus.observe(viewLifecycleOwner) {
            sideAdapter.setSideList(it)
        }
    }
    private fun observeTotalPrice() {
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.totalPrice.text = "${it}원"
        }
    }
}