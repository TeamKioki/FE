package com.umc6th.kioki.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.umc6th.kioki.tutorial.adapter.TabMenuAdapter
import com.umc6th.kioki.tutorial.tabMenus.AlldayKingFragment
import com.umc6th.kioki.tutorial.tabMenus.ChickenAndShrimpFragment
import com.umc6th.kioki.tutorial.tabMenus.NewProductFragment
import com.umc6th.kioki.tutorial.tabMenus.PremiumFragment
import com.umc6th.kioki.tutorial.tabMenus.SideFragment
import com.umc6th.kioki.tutorial.tabMenus.SpecialPackFragment
import com.umc6th.kioki.tutorial.tabMenus.WhopperFragment
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityBurgerKingRealBinding
import com.umc6th.kioki.tutorial.practice.BurgerKingTutorialActivity
import com.umc6th.kioki.tutorial.tabMenus.dialog.SelectTakeInOrOutDialog
import com.umc6th.kioki.tutorial.tutorial.MainTutorialActivity
import com.umc6th.kioki.tutorial.tutorial.StepOneTutorialActivity
import com.umc6th.kioki.tutorial.tutorial.StepTwoTutorialActivity

class BurgerKingRealActivity : AppCompatActivity(), SelectTakeInOrOutDialog.SelectTakeInOrOutListener {
    private lateinit var binding: ActivityBurgerKingRealBinding
    private val viewModel: TutorialViewModel by viewModels()
    override fun onResume() {
        super.onResume()
        viewModel.setScreen(TutorialScreen.Main)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_burger_king_real)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.setScreen(TutorialScreen.Main)

        binding.tutorialItem.setOnClickListener {
            when (viewModel.currentScreen.value) {
                is TutorialScreen.Main -> {
                    startActivity(Intent(this, MainTutorialActivity::class.java))
                }
                is TutorialScreen.StepOne -> {
                    startActivity(Intent(this, StepOneTutorialActivity::class.java))
                }
                is TutorialScreen.StepTwo -> {
                    startActivity(Intent(this, StepTwoTutorialActivity::class.java))
                }
                null -> TODO()
            }
        }

        binding.exitItem.setOnClickListener {
            finish()
        }

        val isPracticeMode = intent.getBooleanExtra("isPracticeMode", false)
        if (isPracticeMode) startActivity(
            Intent(
                this, BurgerKingTutorialActivity::
                class.java
            )
        )
    }

     override fun onTakeInSelected() {
        findNavController(R.id.burgerkingNavHostFragment).navigate(R.id.action_selectTabFragment_to_stepOneFragment)
    }

     override fun onTakeOutSelected() {
        findNavController(R.id.burgerkingNavHostFragment).navigate(R.id.action_selectTabFragment_to_stepOneFragment)
    }
}