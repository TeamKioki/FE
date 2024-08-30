package com.umc6th.kioki

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView

class KioskDeleteDialog(
    context: Context,
    private val name: String
) : Dialog(context) {

    private var confirmListener: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_deletebrand)
        // 다이얼로그의 dimAmount 설정 (0.0에서 1.0 사이의 값, 기본값은 0.5)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setDimAmount(0.85f)  // 배경을 좀 더 어둡게 설정 (0.8f로 설정)

        var brandname: TextView = findViewById(R.id.deletekiosk_brandname_tv)
        brandname.text = name

        // 취소 버튼 설정
        val cancelButton: TextView = findViewById(R.id.deletekiosk_cancel_tv)
        cancelButton.setOnClickListener {
            dismiss() // 다이얼로그 닫기
        }

        // 확인 버튼 설정
        val confirmButton: TextView = findViewById(R.id.deletekiosk_okay_tv)
        confirmButton.setOnClickListener {
            confirmListener?.invoke() // 확인 리스너 호출
            dismiss() // 다이얼로그 닫기
        }
    }

    // 확인 리스너 설정 함수
    fun setOnConfirmListener(listener: () -> Unit) {
        confirmListener = listener
    }
}