package com.umc6th.kioki.onboarding

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.umc6th.kioki.onboarding.adapter.OnboardingAdapter
import com.umc6th.myapplication.R
import com.umc6th.myapplication.databinding.ActivityOnboardingBinding

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
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

        binding.onBoardingViewPager.adapter = onboardingAdapter
        binding.dotsIndicator.attachTo(binding.onBoardingViewPager)
        onboardingAdapter.setFragments(onboardingItems)
    }
}