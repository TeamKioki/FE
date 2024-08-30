package com.umc6th.kioki.kiosk

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.umc6th.kioki.tutorial.BurgerKingRealActivity

class BrandpracticeDialog(
    context: Context,
    private val name: String,
    private val info: String,
    private val logo: Int
) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_brandpractice)

        // 다이얼로그의 dimAmount 설정 (0.0에서 1.0 사이의 값, 기본값은 0.5)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setDimAmount(0.85f)  // 배경을 좀 더 어둡게 설정 (0.8f로 설정)

        var brandname: TextView = findViewById(R.id.dialog_brandname_tv)
        var brandinfo: TextView = findViewById(R.id.dialog_brandinfo_tv)
        var brandlogo: ImageView = findViewById(R.id.dialog_brandlogo_iv)
        val practicemode: Button = findViewById(R.id.dialog_practice_btn)
        val realmode: Button = findViewById(R.id.dialog_real_btn)

        brandname.text = name
        brandinfo.text = info
        brandlogo.setImageResource(logo)  //추후 logo 부분 수정

        practicemode.setOnClickListener {
            val intent = Intent(context, BurgerKingRealActivity::class.java).putExtra(
                "isPracticeMode", true
            )
            context.startActivity(intent)
            dismiss()
            // 연습 모드로 이동
        }

        realmode.setOnClickListener {
            val intent = Intent(context, BurgerKingRealActivity::class.java).putExtra(
                "isPracticeMode", false
            )
            context.startActivity(intent)
            dismiss()
            // 실전 모드로 이동
        }
    }
}