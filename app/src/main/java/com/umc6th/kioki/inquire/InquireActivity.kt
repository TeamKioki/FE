package com.umc6th.kioki.inquire

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityInquireBinding
import com.umc6th.kioki.inquire.adapter.InquireAdapter

class InquireActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInquireBinding
    private val inquireAdapter: InquireAdapter by lazy { InquireAdapter(this) }
    private val fragments = listOf(
        WriteInquireFragment(),
        MyInquiriesFragment()

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inquire)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.viewPager.apply {
            adapter = inquireAdapter
        }
        inquireAdapter.setFragments(fragments)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "문의하기"
                1 -> "나의 문의 내역"
                else -> null
            }
        }.attach()
    }
}