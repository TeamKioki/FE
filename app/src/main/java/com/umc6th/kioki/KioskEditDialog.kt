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
import com.umc6th.kioki.utils.TextPrefs

class KioskEditDialog(
    context: Context,
    private val name: String,
    private val logo: Int
) : Dialog(context) {
    private var selectedColor: Int = Color.BLACK // 기본 색상

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_kioskedit)
        updateTextColor(TextPrefs(context).getTextColor())
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

        val blackColorBtn: ImageView = findViewById(R.id.color1)
        val redColorBtn: ImageView = findViewById(R.id.color2)
        val yellowColorBtn: ImageView = findViewById(R.id.color3)
        val greenColorBtn: ImageView = findViewById(R.id.color4)
        val blueColorBtn: ImageView = findViewById(R.id.color5)
        val purpleColorBtn: ImageView = findViewById(R.id.color6)
        val whiteColorBtn: ImageView = findViewById(R.id.color7)

        val colorButtons = listOf<ImageView>(
            blackColorBtn,
            redColorBtn,
            yellowColorBtn,
            greenColorBtn,
            blueColorBtn,
            purpleColorBtn,
            whiteColorBtn
        )

        val defaultBackground = R.drawable.circle_shape

        val tintList = listOf(
            0xFF000000.toInt(),
            0xFFFC002C.toInt(),
            0xFFF8BE35.toInt(),
            0xFF00B7A1.toInt(),
            0xFF006ED2.toInt(),
            0xFF9338B2.toInt()
        )
        addbtn.setOnClickListener {
            dismiss()
        }
        colorButtons.forEach { button ->

            button.setOnClickListener { view ->
                selectedColor = when (view.id) {
                    blackColorBtn.id -> Color.BLACK
                    redColorBtn.id -> 0xFFFC002C.toInt()
                    yellowColorBtn.id -> 0xFFF8BE35.toInt()
                    greenColorBtn.id -> 0xFF00B7A1.toInt()
                    blueColorBtn.id -> 0xFF006ED2.toInt()
                    purpleColorBtn.id -> 0xFF9338B2.toInt()
                    whiteColorBtn.id -> Color.WHITE
                    else -> Color.BLACK // 기본 색상 (예: Black)
                }
                updateTextColor(selectedColor)
            }
        }
//
//        blackColorBtn.setOnClickListener {
//            selectedColor = Color.BLACK
//            updateTextColor(selectedColor)
//        }
//        redColorBtn.setOnClickListener {
//            selectedColor = 0xFFFC002C.toInt()
//            updateTextColor(selectedColor)
//        }
//        yellowColorBtn.setOnClickListener {
//            selectedColor = 0xFFF8BE35.toInt()
//            updateTextColor(selectedColor)
//        }
//        greenColorBtn.setOnClickListener {
//            selectedColor = 0xFF00B7A1.toInt()
//            updateTextColor(selectedColor)
//        }
//        blueColorBtn.setOnClickListener {
//            selectedColor = 0xFF006ED2.toInt()
//            updateTextColor(selectedColor)
//        }
//        purpleColorBtn.setOnClickListener {
//            selectedColor = 0xFF9338B2.toInt()
//            updateTextColor(selectedColor)
//        }
        brandname.text = name
        brandlogo.setImageResource(logo)

        normal.isChecked = !TextPrefs(context).getTextSize()
        big.isChecked = TextPrefs(context).getTextSize()

        normal.setOnCheckedChangeListener({ buttonView, isChecked ->
            if (isChecked) {
                TextPrefs(context).setTextSize(false)
            }
        })

        big.setOnCheckedChangeListener({ buttonView, isChecked ->
            if (isChecked) {
                TextPrefs(context).setTextSize(true)
            }
        })

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
        TextPrefs(context).setTextColor(color)
    }
}