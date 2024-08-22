package com.umc6th.kioki

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.mrudultora.colorpicker.ColorPickerPopUp
import com.mrudultora.colorpicker.ColorPickerPopUp.OnPickColorListener
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.FragmentGroupHomeEditBinding
import com.umc6th.kioki.utils.TextPrefs
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GroupHomeEditFragmentDialog: DialogFragment() {
    lateinit var binding: FragmentGroupHomeEditBinding // 연결할 xml 파일 가져오기
    private lateinit var apiService: GroupRetrofitInterface
    private lateinit var textPrefs: TextPrefs

    var count: Int = 0

    var memberId: Int = 0
    var memberName:String = ""
    var memberNoteTitle:String = ""
    var memberNoteText:String = ""

    private val originalDimensions = mutableMapOf<View, Pair<Int, Int>>()
    private var isBigSizeApplied = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupHomeEditBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .add(R.id.edit_color_palette_fragmentContainer, GroupHomeColorPalette())
                .commitNow()
        }

        //storeOriginalSizes(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setDimAmount(0.8f)  // 0.8f로 설정하면 더 어두운 효과를 줌
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_group_home_edit_rounded_bg) // 다이얼로그 테두리 둥글게

        // activity에서 전달해준 bundle 값 받기
        var bundle = arguments
        memberId = bundle!!.getInt("MemberId")
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

//        binding.editRadioNormalRb.setOnClickListener {
//            if (isBigSizeApplied) {
//                restoreOriginalSizes(binding.root)
//                isBigSizeApplied = false
//                count = 0
//
//            }
//        }
//
//        binding.editRadioBigRb.setOnClickListener {
//            if(count == 0) {
//                adjustViewSizes(binding.root, scaleFactor = 1.2f)
//                isBigSizeApplied = true
//                count++
//            }
//        }

        // 'x' 아이콘 누르면 다이얼로그 닫히도록 하는 이벤트
        binding.editCancelIv.setOnClickListener {
            dismiss()
        }

        // 연결할 api 설정
        apiService =
            RetrofitClient.create(GroupRetrofitInterface::class.java) // baseurl 뒤에 붙일 url이 있는 인터페이스 파일 연결

        // 서버에서 제공받은 Access Token
        val accessToken =
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGhvbmUiOiIwMTAxMjM0NTY3OCIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MjM3MTU1NTgsImV4cCI6MTcyNjMwNzU1OH0._TI2xGiWqvtNp9ooaf_rRo8puTA1tAZqKoAjADmKwOA"

        // 수정 버튼 이벤트 핸들러
        binding.editModifyBtn.setOnClickListener {
            modifyMember(accessToken, memberId)
        }
        binding.editRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.edit_radio_normal_rb -> {
                    TextPrefs(requireContext()).setTextSize(false)
                }

                R.id.edit_radio_big_rb -> {
                    TextPrefs(requireContext()).setTextSize(true)
                }
            }
        }
    }

    private fun modifyMember(accessToken: String, memberId: Int) {

        val jsonBody = """
        {
            "noteTitle": "${binding.editInputTitleEt.text}",
            "noteBody": "${binding.editInputContentEt.text}",
            "color": "string",
            "fontSize": "NORMAL",
            "nickname": "${binding.editMemberNameEt.text}",
            "profileName": "string"
        }
        """.trimIndent()

        val requestBody = jsonBody.toRequestBody("application/json".toMediaTypeOrNull())

        apiService.modifyMember("Bearer $accessToken", memberId, requestBody).enqueue(object :
            Callback<GroupMemberResponse> {
            override fun onResponse(
                call: Call<GroupMemberResponse>,
                response: Response<GroupMemberResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("통신", "GroupMembers Response: $result")
                } else {
                    Log.e("통신", "ModifyGroup Response 실패: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GroupMemberResponse>, t: Throwable) {
                Log.e("통신", "통신 실패: ${t.message}", t)
            }
        })

    }
    // 모든 뷰의 크기를 조정하는 함수
    private fun adjustViewSizes(view: View, scaleFactor: Float) {
        val layoutParams = view.layoutParams

        if (layoutParams != null) {
            //layoutParams.width = (layoutParams.width * scaleFactor).toInt()
            layoutParams.height = (layoutParams.height * scaleFactor).toInt()
            if (layoutParams is ViewGroup.MarginLayoutParams) {
                layoutParams.leftMargin = (layoutParams.leftMargin * scaleFactor).toInt()
                layoutParams.topMargin = (layoutParams.topMargin * scaleFactor).toInt()
                layoutParams.rightMargin = (layoutParams.rightMargin * scaleFactor).toInt()
                layoutParams.bottomMargin = (layoutParams.bottomMargin * scaleFactor).toInt()
            }
            view.layoutParams = layoutParams
        }

        view.setPadding(
            (view.paddingLeft * scaleFactor).toInt(),
            (view.paddingTop * scaleFactor).toInt(),
            (view.paddingRight * scaleFactor).toInt(),
            (view.paddingBottom * scaleFactor).toInt()
        )

        if (view is TextView) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.textSize * scaleFactor)
        }

        if (view is ViewGroup) {
            for (child in view.children) {
                adjustViewSizes(child, scaleFactor)
            }
        }

    }
//    private fun storeOriginalSizes(view: View) {
//        view.layoutParams?.let { layoutParams ->
//            originalDimensions[view] = Pair(layoutParams.width, layoutParams.height)
//        }
//        if (view is ViewGroup) {
//            for (child in view.children) {
//                storeOriginalSizes(child)
//            }
//        }
//    }
//
//    private fun restoreOriginalSizes(view: View) {
//        val originalSize = originalDimensions[view]
//        if (originalSize != null) {
//            val layoutParams = view.layoutParams
//            layoutParams.width = originalSize.first
//            layoutParams.height = originalSize.second
//            view.layoutParams = layoutParams
//        }
//        if (view is ViewGroup) {
//            for (child in view.children) {
//                restoreOriginalSizes(child)
//            }
//        }
//    }
    fun radioBtnClickEvent(view: View) {
        val isSelected: Boolean = (view as? AppCompatRadioButton)?.isChecked ?: false
        if(isSelected) {

        }
    }



}
