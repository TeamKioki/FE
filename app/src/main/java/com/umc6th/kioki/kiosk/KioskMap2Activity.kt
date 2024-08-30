package com.umc6th.kioki.kiosk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityKioskmap2Binding

class KioskMap2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityKioskmap2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKioskmap2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kioskmapBackBtn.setOnClickListener {
            finish()
        }

        binding.kioskmapGopracticeBtn.setOnClickListener {
            // Start the KioskhomeActivity
//            val intent = Intent(this, KioskhomeActivity::class.java)
//            startActivity(intent)

            // After starting KioskhomeActivity, show the BrandpracticeDialog
            val dialog = BrandpracticeDialog(
                this,
                "버거킹",  // You can replace this with the actual brand name or pass it through Intent
                "햄버거 · 패스트푸드점",  // You can replace this with the actual brand info or pass it through Intent
                R.drawable.logo_burgerking // Replace with actual logo resource id
            )
            dialog.show()
        }
    }
}