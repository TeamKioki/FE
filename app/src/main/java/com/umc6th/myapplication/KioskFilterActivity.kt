package com.umc6th.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc6th.myapplication.databinding.ActivityKioskfilterBinding

class KioskFilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKioskfilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKioskfilterBinding.inflate(layoutInflater)


    }
}