package com.umc6th.kioki.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.umc6th.kioki.join.JoinActivity
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.joinTextView.setOnClickListener {
            startActivity(Intent(this, JoinActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
//            viewModel.requestAuthCode(binding.inputPhoneNumber.text.toString())
            if (binding.inputPhoneNumber.text.toString().length != 11) {
                // handle error
                Toast.makeText(this, "휴대폰 번호를 정확히 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(this, InputLoginCodeActivity::class.java).putExtra(
                "phone", binding.inputPhoneNumber.text.toString()
            ))
        }

    }
}