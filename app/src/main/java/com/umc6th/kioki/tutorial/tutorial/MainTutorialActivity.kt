package com.umc6th.kioki.tutorial.tutorial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityMainTutorialBinding
import com.umc6th.kioki.utils.TextPrefs

class MainTutorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainTutorialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_tutorial)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textSize = TextPrefs(this).getTextSize()
        if (textSize) {
            binding.apply {
                text1.textSize = 20f
                text2.textSize = 20f
                text3.textSize = 20f
            }
        }
        binding.apply {
            text1.textSize = 20f
            text2.textSize = 20f
            text3.textSize = 15f
        }
        binding.tutorialCloseButton.setOnClickListener {
            finish()
        }
    }
}