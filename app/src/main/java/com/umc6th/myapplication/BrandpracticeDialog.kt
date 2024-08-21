package com.umc6th.myapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class BrandpracticeDialog(
    context: Context,
    private val name: String,
    private val info: String,
    private val logo: String
) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_brandpractice)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var brandname: TextView = findViewById(R.id.dialog_brandname_tv)
        var brandinfo: TextView = findViewById(R.id.dialog_brandinfo_tv)
        var brandlogo: ImageView = findViewById(R.id.dialog_brandlogo_iv)
        val practicemode: Button = findViewById(R.id.dialog_practice_btn)
        val realmode: Button = findViewById(R.id.dialog_real_btn)

        brandname.text = name
        brandinfo.text = info
        //brandlogo.setImageResource(logo)  //추후 logo 부분 수정

        practicemode.setOnClickListener {
            // 연습 모드로 이동
            dismiss()
        }

        realmode.setOnClickListener {
            // 실전 모드로 이동
            dismiss()
        }
    }
}