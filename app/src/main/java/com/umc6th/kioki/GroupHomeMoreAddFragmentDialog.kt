package com.umc6th.kioki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.umc6th.kioki.databinding.FragmentGroupHomeEditBinding
import com.umc6th.kioki.databinding.FragmentGroupHomeMoreAddBinding

class GroupHomeMoreAddFragmentDialog: DialogFragment() {
    lateinit var binding: FragmentGroupHomeMoreAddBinding // 연결할 xml 파일 가져오기
    var memberImg:Int = 0
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
        binding = FragmentGroupHomeMoreAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setDimAmount(0.8f)  // 0.8f로 설정하면 더 어두운 효과를 줌
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_group_home_edit_rounded_bg) // 다이얼로그 테두리 둥글게

        // activity에서 전달해준 bundle 값 받기
        var bundle = arguments
        memberImg = bundle!!.getInt("MemberImg")
        memberName = bundle!!.getString("MemberName").toString()
        memberNoteTitle = bundle!!.getString("MemberNoteTitle").toString()
        memberNoteText = bundle!!.getString("MemberNoteText").toString()

        binding.moreAddMemberImgIv.setImageResource(memberImg)
        binding.moreAddMemberNameTv.setText(memberName)
        binding.moreAddInputTitleEt.setText(memberNoteTitle)
        binding.moreAddInputContentEt.setText(memberNoteText)

        // 'x' 아이콘 누르면 다이얼로그 닫히도록 하는 이벤트
        binding.moreAddCancelIv.setOnClickListener {
            dismiss()
        }

        // 그룹 추가 버튼 클릭 이벤트
        binding.moreAddGroupBtn.setOnClickListener {

        }

    }
}