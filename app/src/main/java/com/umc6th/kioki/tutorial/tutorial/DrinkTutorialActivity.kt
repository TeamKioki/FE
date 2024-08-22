package com.umc6th.kioki.tutorial.tutorial

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityDrinkTutorialBinding
import com.umc6th.kioki.utils.TextPrefs

class DrinkTutorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrinkTutorialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drink_tutorial)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (TextPrefs(this).getTextSize()) {
            binding.apply {
                text1.textSize = 20f
                text2.textSize = 20f
            }
        }
        binding.apply {
            TextPrefs(this@DrinkTutorialActivity).getTextColor().let {
                text1.setTextColor(it)
                text2.setTextColor(it)
            }
        }
        binding.tutorialCloseButton.setOnClickListener {
            finish()
        }
    }
}