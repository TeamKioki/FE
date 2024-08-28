package com.umc6th.kioki

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView

class KioskAllApplyDialog(
    context: Context
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_allkiosk)


        // 다이얼로그의 dimAmount 설정 (0.0에서 1.0 사이의 값, 기본값은 0.5)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setDimAmount(0.9f)  // 배경을 좀 더 어둡게 설정 (0.8f로 설정)

        val cancelbtn: TextView = findViewById(R.id.allkiosk_cancle_tv)
        val okaybtn: TextView = findViewById(R.id.allkiosk_okay_tv)

        cancelbtn.setOnClickListener {
            dismiss()
        }

        okaybtn.setOnClickListener {
            dismiss()
        }
    }
}