package com.umc6th.kioki.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.umc6th.kioki.onboarding.adapter.OnboardingAdapter
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityOnboardingBinding
import com.umc6th.kioki.login.LoginActivity
import com.umc6th.kioki.tutorial.practice.finish.PracticeFinishActivity

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

        // 페이지 변경 리스너 추가
        binding.onBoardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateButtonVisibility(position)
            }
        })

        // 초기 상태를 위해 버튼 가시성 업데이트
        updateButtonVisibility(binding.onBoardingViewPager.currentItem)

        binding.startTextView.setOnClickListener {
//            startActivity(Intent(this, PracticeFinishActivity::class.java))
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.nextButton.setOnClickListener {
            val nextItem = binding.onBoardingViewPager.currentItem + 1
            if (nextItem < onboardingItems.size) {
                binding.onBoardingViewPager.currentItem = nextItem
            }
        }
    }

    private fun updateButtonVisibility(position: Int) {
        binding.nextButton.visibility = if (position == onboardingItems.size - 1) View.INVISIBLE else View.VISIBLE
        binding.startTextView.visibility = if (position == onboardingItems.size - 1) View.VISIBLE else View.INVISIBLE
    }
}