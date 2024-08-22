package com.umc6th.kioki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc6th.kioki.databinding.ActivityKioskmapBinding

class KioskMapActivity: AppCompatActivity() {
    lateinit var binding: ActivityKioskmapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKioskmapBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}