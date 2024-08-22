package com.umc6th.kioki

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView

class KioskEditDialog(
    context: Context,
    private val name: String
    //private val profile: Int
) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_kioskedit)

        // 다이얼로그의 dimAmount 설정 (0.0에서 1.0 사이의 값, 기본값은 0.5)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setDimAmount(0.85f)  // 배경을 좀 더 어둡게 설정 (0.8f로 설정)

        var username: TextView = findViewById(R.id.kioskedit_name_tv)
        val editbtn: ImageView = findViewById(R.id.kioskedit_edit_btn)
        val userprofile: ImageView = findViewById(R.id.kioskedit_profile_iv)
        val normal: RadioButton = findViewById(R.id.kioskedit_normal_rbtn)
        val big: RadioButton = findViewById(R.id.kioskedit_big_rbtn)

        val closebtn: ImageView = findViewById(R.id.kioskedit_close_btn)
        val allkiosk: Button = findViewById(R.id.kioskedit_allkiosk_btn)
        val addbtn: Button = findViewById(R.id.kioskedit_add_btn)

        username.text = name
        //userprofile.setImageResource(profile)

        closebtn.setOnClickListener{
            dismiss()
        }

        allkiosk.setOnClickListener {
            dismiss()
            // 다이얼로그 띄우기
        }
    }
}