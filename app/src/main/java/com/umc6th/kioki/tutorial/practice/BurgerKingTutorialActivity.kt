package com.umc6th.kioki.tutorial.practice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityBurgerKingTutorialBinding
import com.umc6th.kioki.tutorial.adapter.PracticeAdapter
import com.umc6th.kioki.tutorial.practice.finish.PracticeFinishActivity
import com.umc6th.kioki.utils.TextPrefs

class BurgerKingTutorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBurgerKingTutorialBinding
    private val practiceAdapter: PracticeAdapter by lazy { PracticeAdapter(this) }
    private val fragments = listOf(
        MenuSelectFragment(),
        OptionSelectFragment(),
        SidePracticeFragment(),
        DrinkFragment(),
        CartFragment(),
        TakeOutFragment(),
        PayFragment(),
        SelectPayFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_burger_king_tutorial)
        enableEdgeToEdge()
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//        )
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//        )
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val textSize = TextPrefs(this).getTextSize()
        if (textSize) {
            binding.apply {

            }
        }
        binding.fragmentContainerView2.adapter = practiceAdapter
        practiceAdapter.setFragments(fragments)


        // 페이지 변경 리스너 추가
        binding.fragmentContainerView2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateButtonVisibility(position)
            }
        })

        // 초기 상태를 위해 버튼 가시성 업데이트
        updateButtonVisibility(binding.fragmentContainerView2.currentItem)

        binding.nextButton.setOnClickListener {
            val nextItem = binding.fragmentContainerView2.currentItem + 1
            if (nextItem < fragments.size) {
                binding.fragmentContainerView2.currentItem = nextItem
            }

            if (nextItem == fragments.size) {
                startActivity(Intent(this, PracticeFinishActivity::class.java))
                finish()
            }
        }

        binding.previousButton.setOnClickListener {
            val prevItem = binding.fragmentContainerView2.currentItem - 1
            if (prevItem >= 0) {
                binding.fragmentContainerView2.currentItem = prevItem
            }
        }

    }

    private fun updateButtonVisibility(position: Int) {
        binding.previousButton.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE
    }
}