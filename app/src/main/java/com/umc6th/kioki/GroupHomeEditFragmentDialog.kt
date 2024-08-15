package com.umc6th.kioki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.mrudultora.colorpicker.ColorPickerPopUp
import com.mrudultora.colorpicker.ColorPickerPopUp.OnPickColorListener
import com.umc6th.kioki.databinding.FragmentGroupHomeEditBinding

class GroupHomeEditFragmentDialog: DialogFragment() {
    lateinit var binding: FragmentGroupHomeEditBinding // 연결할 xml 파일 가져오기

    var memberName:String = ""
    var memberNoteTitle:String = ""
    var memberNoteText:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupHomeEditBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setDimAmount(0.8f)  // 0.8f로 설정하면 더 어두운 효과를 줌
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_group_home_edit_rounded_bg) // 다이얼로그 테두리 둥글게

        // activity에서 전달해준 bundle 값 받기
        var bundle = arguments
        memberName = bundle!!.getString("MemberName").toString()
        memberNoteTitle = bundle!!.getString("MemberNoteTitle").toString()
        memberNoteText = bundle!!.getString("MemberNoteText").toString()

        binding.editMemberNameEt.setText(memberName)
        binding.editInputTitleEt.setText(memberNoteTitle)
        binding.editInputContentEt.setText(memberNoteText)


        // 라디오버튼 색상 지정 -> 안됨....
        val radioButton = binding.editRadioNormalRb
        val colorStateList = ContextCompat.getColorStateList(requireContext(), R.color.selector_group_edit_radiobtn)
        radioButton.buttonTintList = colorStateList

        // 'x' 아이콘 누르면 다이얼로그 닫히도록 하는 이벤트
        binding.editCancelIv.setOnClickListener {
            dismiss()
        }

        binding.editAddColorBtnIv.setOnClickListener {
            // 현재 DialogFragment 닫기
            dismiss()

            // GroupHomeEditColorPickerFragment 다이얼로그 열기
//            val ColorPickerDialogFragment = GroupHomeEditColorPickerFragment()
//            ColorPickerDialogFragment.show(parentFragmentManager, "ColorPickerDialogFragment")
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

    fun radioBtnClickEvent(view: View) {
        val isSelected: Boolean = (view as? AppCompatRadioButton)?.isChecked ?: false
        if(isSelected) {

        }
    }
}