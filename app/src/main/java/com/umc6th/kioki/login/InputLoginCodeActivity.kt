package com.umc6th.kioki.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.umc6th.kioki.MainActivity
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityInputLoginCodeBinding

class InputLoginCodeActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityInputLoginCodeBinding
    lateinit var certNum: Array<EditText>
    var cert: String = ""
    var phone: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_input_login_code)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        certNum = arrayOf(
            binding.cert1,
            binding.cert2,
            binding.cert3,
            binding.cert4,
            binding.cert5,
            binding.cert6,
            binding.cert7,
            binding.cert8
        )
//        phone = intent.getStringExtra("phone")!!
//        viewModel.requestAuthCode(phone)
        phone = "01012345667"
        binding.loginButton.setOnClickListener(onClickButtonListener())
        onDelKeyListener()
        setCertNumOnTextChangedListener()
        viewModel.loginState.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
//            if (it) {
//
//            }
        }
    }

    private fun onClickButtonListener(): View.OnClickListener? {
        return View.OnClickListener {
            var temp = ""
            for (i in 0..7) temp += certNum[i].text.toString()
            if (temp.length != 8)
                Toast.makeText(this, "인증번호를 정확히 입력해주세요", Toast.LENGTH_SHORT).show()
            else {
                cert = ""
                for (i in 0..7) cert += certNum[i].text.toString()
                Log.d("INPUTLOGINCODEACTIVITY", "onClickButtonListener: $cert ")
//                viewModel.login(phone, cert)
            }
        }
    }
    private fun onDelKeyListener() {
        for (idx in 1..7) certNum[idx].setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                certNum[idx - 1].requestFocus()
            }
            false
        }
    }
    private fun setCertNumOnTextChangedListener() {
        for (idx in 0 until certNum.size -1) certNum[idx].addTextChangedListener {
            if (certNum[idx].text.toString().length == 1) {
                certNum[idx + 1].requestFocus()
            }
        }
    }
}