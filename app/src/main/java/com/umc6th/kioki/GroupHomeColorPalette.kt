package com.umc6th.kioki

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
//            val ColorPickerDialogFragment = GroupHomeEditColorPickerFragment()
//            ColorPickerDialogFragment.show(parentFragmentManager, "ColorPickerDialogFragment")
            val colorPickerPopUp = ColorPickerPopUp(context) // Pass the context.
            colorPickerPopUp.setShowAlpha(true) // By default show alpha is true.
                .setDialogTitle("Pick a Color")
                .setOnPickColorListener(object : OnPickColorListener {
                    override fun onColorPicked(color: Int) {
                        // handle the use of color
                        val newColorView = View(requireContext()).apply {
                            layoutParams = GridLayout.LayoutParams().apply {
                                width = 32.dpToPx()
                                height = 32.dpToPx()
                                setMargins(8.dpToPx(), 8.dpToPx(), 8.dpToPx(), 8.dpToPx())
                            }
                            background = ContextCompat.getDrawable(requireContext(), R.drawable.shape_color_palette)
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