package com.umc6th.kioki.tutorial.tabMenus.dialog

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentSelectTakeInOrOutDialogBinding
import com.umc6th.kioki.tutorial.tutorial.TakeInOrOutTutorialActivity
import com.umc6th.kioki.utils.dialogFragmentResize

class SelectTakeInOrOutDialog : DialogFragment() {

    private lateinit var binding: FragmentSelectTakeInOrOutDialogBinding
    private var listener: SelectTakeInOrOutListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listener = activity as? SelectTakeInOrOutListener
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this, 0.95f, 0.4f)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectTakeInOrOutDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tutorialItem.setOnClickListener {
                startActivity(Intent(requireContext(), TakeInOrOutTutorialActivity::class.java))
            }
            takeInButton.setOnClickListener {
                listener?.onTakeInSelected()
                dismiss()
            }
            takeOutButton.setOnClickListener {
                listener?.onTakeOutSelected()
                dismiss()
            }
        }
    }

    interface SelectTakeInOrOutListener {
        fun onTakeInSelected()
        fun onTakeOutSelected()
    }
}