package com.umc6th.kioki.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityRealEndBinding
import com.umc6th.kioki.tutorial.practice.finish.PracticeFinishActivity

class RealEndActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRealEndBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_real_end)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.retryButton.setOnClickListener {
            startActivity(Intent(this, BurgerKingRealActivity::class.java).apply {
                putExtra("isPracticeMode", false)
            })
            finish()
        }

        binding.nextButton.setOnClickListener {
            finish()
        }
    }
}