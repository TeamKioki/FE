package com.umc6th.kioki.kiosk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.umc6th.kioki.databinding.FragmentAccountEditKioskSettingBinding
import com.umc6th.kioki.group.DialogListener
import com.umc6th.kioki.group.GroupHomeColorPalette

class KioskSettingFragmentDialog : DialogFragment() {
    lateinit var binding: FragmentAccountEditKioskSettingBinding // 연결할 xml 파일 가져오기
    private var listener: DialogListener? = null
    val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsInBob25lIjoiMDEwODI0NzMwMTAiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzI0MzE5MjM5LCJleHAiOjE3MjY5MTEyMzl9.Zwz108s5qKDBo02nm16H_Ma_P0CnkUybG66XbkOk9_A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountEditKioskSettingBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .add(R.id.edit_color_palette_fragmentContainer, GroupHomeColorPalette())
                .commitNow()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setDimAmount(0.8f)  // 0.8f로 설정하면 더 어두운 효과를 줌
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_group_home_edit_rounded_bg) // 다이얼로그 테두리 둥글게

        // activity에서 전달해준 bundle 값 받기
        var bundle = arguments
//        memberId = bundle!!.getInt("MemberId")
//        memberName = bundle!!.getString("MemberName").toString()
//        memberNoteTitle = bundle!!.getString("MemberNoteTitle").toString()
//        memberNoteText = bundle!!.getString("MemberNoteText").toString()
//
//        binding.editMemberNameEt.setText(memberName)
//        binding.editInputTitleEt.setText(memberNoteTitle)
//        binding.editInputContentEt.setText(memberNoteText)


        // 라디오버튼 색상 지정 -> 안됨....
        val radioButton = binding.editRadioNormalRb
        val colorStateList = ContextCompat.getColorStateList(requireContext(), R.color.selector_group_edit_radiobtn)
        radioButton.buttonTintList = colorStateList

        // 'x' 아이콘 누르면 다이얼로그 닫히도록 하는 이벤트
        binding.editCancelIv.setOnClickListener {
            dismiss()
        }

        // 연결할 api 설정
//        apiService =
//            RetrofitClient.create(GroupRetrofitInterface::class.java) // baseurl 뒤에 붙일 url이 있는 인터페이스 파일 연결

        // 서버에서 제공받은 Access Token

        // 수정 버튼 이벤트 핸들러
        binding.editModifyBtn.setOnClickListener {
//            modifyMember(accessToken, memberId)

            //listener?.onModifiedDataReceived()

        }

    }
}