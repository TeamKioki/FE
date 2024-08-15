package com.umc6th.kioki.onboarding

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.umc6th.kioki.onboarding.adapter.OnboardingAdapter
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private val onboardingAdapter by lazy { OnboardingAdapter(this) }
    private val onboardingItems = listOf(
        FirstFragment(),
        SecondFragment(),
        ThirdFragment(),
        FourthFragment(),
        LastFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.onBoardingViewPager.adapter = onboardingAdapter
        binding.dotsIndicator.attachTo(binding.onBoardingViewPager)
        onboardingAdapter.setFragments(onboardingItems)
    }
}