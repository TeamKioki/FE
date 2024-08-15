package com.umc6th.kioki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mrudultora.colorpicker.ColorPickerPopUp
import com.mrudultora.colorpicker.ColorPickerPopUp.OnPickColorListener
import com.umc6th.kioki.databinding.FragmentGroupHomeEditColorpickerBinding


class GroupHomeEditColorPickerFragment: DialogFragment() {
    lateinit var binding: FragmentGroupHomeEditColorpickerBinding // 연결할 xml 파일 가져오기

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupHomeEditColorpickerBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setDimAmount(0.8f)  // 0.8f로 설정하면 더 어두운 효과를 줌

        // ColorPickerDialog 생성 및 보여주기
//        ColorPickerDialog.Builder(context)
//            .setTitle("Pick a Color")
//            .setPreferenceName("C olorPickerDialog")
//            .setPositiveButton(getString(android.R.string.ok),
//                ColorEnvelopeListener { envelope, _ ->
//                    // 선택한 색상 값을 처리
//                    //binding.colorPreview.setBackgroundColor(envelope.color)
//                })
//            .setNegativeButton(getString(android.R.string.cancel)) { dialogInterface, _ -> dialogInterface.dismiss() }
//            .attachAlphaSlideBar(true)  // alpha slide bar (default: true)
//            .attachBrightnessSlideBar(true)  // brightness slide bar (default: true)
//            .setBottomSpace(12)  // sets a bottom space between the last slidebar and buttons
//            .show()  // shows the dialog


        val colorPickerPopUp = ColorPickerPopUp(context) // Pass the context.
        colorPickerPopUp.setShowAlpha(true) // By default show alpha is true.
            .setDialogTitle("Pick a Color")
            .setOnPickColorListener(object : OnPickColorListener {
                override fun onColorPicked(color: Int) {
                    // handle the use of color
                }

                override fun onCancel() {
                    colorPickerPopUp.dismissDialog() // Dismiss the dialog.
                }
            })
            .show()
    }
}