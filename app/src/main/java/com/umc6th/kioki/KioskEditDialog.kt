package com.umc6th.kioki

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView

class KioskEditDialog(
    context: Context,
    private val name: String,
    private val logo: Int
) : Dialog(context) {
    private var selectedColor: Int = Color.BLACK // 기본 색상

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_kioskedit)

        // 다이얼로그의 dimAmount 설정 (0.0에서 1.0 사이의 값, 기본값은 0.5)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setDimAmount(0.85f)  // 배경을 좀 더 어둡게 설정 (0.8f로 설정)

        var brandname: TextView = findViewById(R.id.kioskedit_name_et)
        val editbtn: ImageView = findViewById(R.id.kioskedit_edit_btn)
        val brandlogo: ImageView = findViewById(R.id.item_brandlogo_iv)
        val normal: RadioButton = findViewById(R.id.kioskedit_normal_rbtn)
        val big: RadioButton = findViewById(R.id.kioskedit_big_rbtn)

        val closebtn: ImageView = findViewById(R.id.kioskedit_close_btn)
        val allkiosk: Button = findViewById(R.id.kioskedit_allkiosk_btn)
        val addbtn: Button = findViewById(R.id.kioskedit_add_btn)

        brandname.text = name
        brandlogo.setImageResource(logo)

        closebtn.setOnClickListener{
            dismiss()
        }

        allkiosk.setOnClickListener {
            dismiss()
            // 다이얼로그 띄우기
        }

        // GridLayout에서 색상 클릭 리스너 설정
//        val colorPalette = findViewById<GridLayout>(R.id.color_palette_grid)
//        for (i in 0 until colorPalette.childCount) {
//            val colorView = colorPalette.getChildAt(i)
//            colorView.setOnClickListener {
//                selectedColor = colorView.tag as Int
//                updateTextColor(selectedColor)
//            }
//        }
    }

    private fun updateTextColor(color: Int) {
        val brandname: TextView = findViewById(R.id.kioskedit_name_et)
        brandname.setTextColor(color)
    }
}