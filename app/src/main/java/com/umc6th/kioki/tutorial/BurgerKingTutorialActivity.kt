package com.umc6th.kioki.tutorial

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.umc6th.kioki.R

class BurgerKingTutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//        )
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//        )
        setContentView(R.layout.activity_burger_king_tutorial)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}