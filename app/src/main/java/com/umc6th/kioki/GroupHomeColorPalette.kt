package com.umc6th.kioki

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mrudultora.colorpicker.ColorPickerPopUp
import com.mrudultora.colorpicker.ColorPickerPopUp.OnPickColorListener
import com.umc6th.kioki.databinding.FragmentColorPaletteBinding
import com.umc6th.kioki.databinding.FragmentGroupHomeEditColorpickerBinding

class GroupHomeColorPalette : Fragment() {
    lateinit var binding: FragmentColorPaletteBinding
    private lateinit var colorGrid: GridLayout
    private lateinit var addColorButton: ImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 인플레이트
        binding = FragmentColorPaletteBinding.inflate(layoutInflater)

        colorGrid = binding.colorPaletteGrid
        addColorButton = binding.editAddColorBtnIv

        // 초기 뷰에 대한 클릭 리스너 설정
        setupColorViewListeners()

        addColorButton.setOnClickListener {
            // GroupHomeEditColorPickerFragment 다이얼로그 열기
            val colorPickerPopUp = ColorPickerPopUp(context)
            colorPickerPopUp.setShowAlpha(true)
                .setDialogTitle("색상 추가")
                .setPositiveButtonText("추가")
                .setNegativeButtonText("취소")
                .setOnPickColorListener(object : OnPickColorListener {
                    override fun onColorPicked(color: Int) {
                        // handle the use of color
                        val newColorView = View(requireContext()).apply {
                            // 원 모양의 Drawable 생성
                            val drawable = GradientDrawable().apply {
                                shape = GradientDrawable.OVAL
                                setColor(color) // 선택된 색상 적용
                                setSize(32.dpToPx(), 32.dpToPx()) // 크기 설정
                            }
                            layoutParams = GridLayout.LayoutParams().apply {
                                width = 32.dpToPx()
                                height = 32.dpToPx()
                                setMargins(8.dpToPx(), 8.dpToPx(), 8.dpToPx(), 8.dpToPx())
                            }
                            background = drawable
                            //setBackgroundColor(color) // 배경 색상 설정

                            tag = color
                            isClickable = true
                            setOnClickListener { onColorClick(this) } // 클릭 리스너 추가
                        }

                        // GridLayout의 마지막 위치에 새로운 색상 추가
                        colorGrid.addView(newColorView, colorGrid.childCount - 1)
                    }

                    override fun onCancel() {
                        colorPickerPopUp.dismissDialog() // 취소 선택 시 다이얼로그 닫기
                    }
                })
                .show()

        }



        return binding.root
    }
    // 초기 뷰에 대해 클릭 리스너 설정
    private fun setupColorViewListeners() {
        for (i in 0 until colorGrid.childCount) {
            val childView = colorGrid.getChildAt(i)
            // 색상을 동적으로 변경
            val colorResId = view?.tag as? Int ?: return
            val color = ContextCompat.getColor(requireContext(), colorResId)
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.shape_color_palette) as GradientDrawable
            drawable.setColor(color)
            view?.background = drawable

            childView?.setOnClickListener { onColorClick(childView) }
        }
    }
    // 클릭 시 선택 상태를 토글하는 함수
    fun onColorClick(view: View) {
        view.isSelected = !view.isSelected
        Log.d("색상클릭", "클릭")
    }

    private fun Int.dpToPx(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }
}