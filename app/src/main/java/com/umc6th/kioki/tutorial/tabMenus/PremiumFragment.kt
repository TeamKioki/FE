package com.umc6th.kioki.tutorial.tabMenus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.umc6th.kioki.tutorial.MenuItem
import com.umc6th.kioki.tutorial.TutorialViewModel
import com.umc6th.kioki.tutorial.adapter.PremiumAdapter
import com.umc6th.kioki.tutorial.tabMenus.dialog.SelectOptionDialog
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentPremiumBinding
import com.umc6th.kioki.tutorial.TutorialScreen

class PremiumFragment : Fragment() {
    private lateinit var binding: FragmentPremiumBinding
    private val viewModel: TutorialViewModel by activityViewModels()
    private val premiumAdapter: PremiumAdapter by lazy {
        PremiumAdapter {
            viewModel.setMenuItem(it)
            SelectOptionDialog().show(childFragmentManager, "SelectOptionDialog")
        }
    }

    private val premiumItems = listOf(
        MenuItem(
            R.drawable.blackbbqquttuor,
            "블랙바비큐콰트로치즈와퍼",
            9300,
        ),
        MenuItem(
            R.drawable.blackbbq,
            "블랙바비큐와퍼",
            9300
        ),
        MenuItem(
            R.drawable.monster,
            "몬스터와퍼",
            9300
        ),
        MenuItem(
            R.drawable.shrimp,
            "통새우와퍼",
            9300
        ),
        MenuItem(
            R.drawable.cheese,
            "콰트로치즈와퍼",
            9300
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPremiumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setScreen(
            TutorialScreen.Main
        )
        binding.premiumRv.apply {
            adapter = premiumAdapter
            layoutManager = GridLayoutManager(context, 3)
            setHasFixedSize(true)
        }
        premiumAdapter.setList(premiumItems)
    }

}