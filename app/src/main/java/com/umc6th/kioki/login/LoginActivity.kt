package com.umc6th.kioki.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.umc6th.kioki.join.JoinActivity
import com.umc6th.myapplication.R
import com.umc6th.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        
        binding.joinTextView.setOnClickListener {
            startActivity(Intent(this, JoinActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
//            viewModel.executeLogin(binding.phoneEditText.text.toString(), binding.codeEditText.text.toString())
        }

        viewModel.loginState.observe(this) {
            if (it) {
                // login success
            } else {
                // login fail
            }
        }
    }
}