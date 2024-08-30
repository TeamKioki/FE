package com.umc6th.kioki.group

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.umc6th.kioki.R
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
    private var imageUri: Uri? = null

    var count: Int = 0
    var memberId: Int = 0
    var memberName:String = ""
    var memberNoteTitle:String = ""
    var memberNoteText:String = ""

    private val originalTextSizes = mutableMapOf<TextView, Float>()
    private var isBigSizeApplied = false

    private var currentTheme: Int? = null // 텍스트뷰 기본 크기
    private lateinit var pref : DefaultPreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pref = DefaultPreferenceManager(requireContext())

//        // 글자 크기에 따라 테마 설정
//        val textSize = pref.getTextSize()
//        currentTheme = getAppTheme(textSize)
//        requireActivity().setTheme(currentTheme)
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

        // 기본 텍스트 크기 저장
        saveOriginalTextSizes(binding.root)

        // 라디오버튼 색상 지정 -> 안됨....
        val radioButton = binding.editRadioNormalRb
        val colorStateList = ContextCompat.getColorStateList(requireContext(),
            R.color.selector_group_edit_radiobtn
        )
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

        binding.editGroupProfileImgIv.clipToOutline = true
        // 이미지 설정
        binding.editGroupProfileImgIv.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery()
                Log.d("클릭", "이미지 클릭")

                // 버튼 텍스트 숨기기
                //button.text = null
            } else {
                Log.d("클릭", "이미지 설정 클릭")
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }
        // 수정 버튼 이벤트 핸들러
        binding.editModifyBtn.setOnClickListener {
            modifyMember(accessToken, memberId)
            // 수정된 텍스트를 부모 Activity에 전달하고 업데이트
            val newMemberName = binding.editMemberNameEt.text.toString()
            val newNoteTitle = binding.editInputTitleEt.text.toString()
            val newNoteText = binding.editInputContentEt.text.toString()

            Log.d("그룹", "memberId: ${memberId}")
            // 선택된 라디오 버튼 확인
            val selectedTheme = when (binding.editRadioGroup.checkedRadioButtonId) {
                R.id.edit_radio_normal_rb -> R.style.Theme_App_Medium
                R.id.edit_radio_big_rb -> R.style.Theme_App_Large
                else -> R.style.Theme_App_Medium
            }
            // 테마를 저장
            val pref = DefaultPreferenceManager(requireContext())
            pref.setTextSize(
                when (selectedTheme) {
                    R.style.Theme_App_Small -> 0
                    R.style.Theme_App_Medium -> 1
                    R.style.Theme_App_Large -> 2
                    else -> 1
                }
            )
            // 전달하기 위해 인텐트 사용 (또는 다른 방법으로 업데이트 가능)
            val activity = activity as? GroupHomeActivity
            activity?.updateMemberData(memberId, newMemberName, newNoteTitle, newNoteText, imageUri, selectedTheme)



            // GroupHomeActivity를 재생성하여 새로운 테마 적용
            activity?.let {
                it.setTheme(selectedTheme)
                //it.recreate()
            }
        }

//        // 테마 변경에 따라 재설정
//        currentTheme = R.style.Theme_App_Medium.toInt()
//        val textSize = pref.getTextSize()
//        val newTheme = getAppTheme(textSize)
//        if (currentTheme != newTheme) {
//            currentTheme = newTheme
//            requireActivity().setTheme(currentTheme!!)
//        }

        applyTheme()

        // 글자 크기
        binding.editRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.edit_radio_normal_rb -> {
                    //TextPrefs(requireContext()).setTextSize(false)
                    pref.setTextSize(1)
//                    refreshDialog()
                    //setAppTheme(R.style.Theme_App_Medium)
                    updateTextSize(1)
                }

                R.id.edit_radio_big_rb -> {
                    //TextPrefs(requireContext()).setTextSize(true)
                    pref.setTextSize(2)
//                    //refreshDialog()
//                    onResume()
                    //setAppTheme(R.style.Theme_App_Large)
                    updateTextSize(2)

                }
            }
        }
    }

    private fun saveOriginalTextSizes(view: View) {
        if (view is TextView) {
            originalTextSizes[view] = view.textSize // Save the original text size in pixels
        }

        if (view is ViewGroup) {
            for (child in view.children) {
                saveOriginalTextSizes(child)
            }
        }
    }

    private fun applyTheme() {
        val textSize = pref.getTextSize()
        updateTextSize(textSize)
    }
    private fun updateTextSize(textSize: Int) {
        val scaleFactor = when (textSize) {
            0 -> 0.8f // 작은 텍스트 크기
            1 -> 0.9f // 기본 텍스트 크기
            2 -> 1.2f // 큰 텍스트 크기
            else -> 1.0f
        }

        // TextView의 텍스트 크기 변경
        originalTextSizes.forEach { (textView, originalSize) ->
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, originalSize * scaleFactor)
        }
    }

    // 갤러리 open
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery()
            }
        }

    // 가져온 사진 보여주기
    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let {
                    imageUri = it
                    binding.editGroupProfileImgIv.setImageURI(imageUri)
                }
            }
        }
    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(gallery)
    }

    private fun adjustViewSizes(view: View, scaleFactor: Float) {
        if (view is TextView) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.textSize * scaleFactor)
        }

        if (view is ViewGroup) {
            for (child in view.children) {
                adjustViewSizes(child, scaleFactor)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("그룹",  requireActivity().theme.toString())
        refreshDialog()
    }

    private fun getAppTheme(textSize: Int): Int {
        return when (textSize) {
            0 -> R.style.Theme_App_Small // 작은 글씨 테마
            1 -> R.style.Theme_App_Medium // 기본 글씨 테마
            2 -> R.style.Theme_App_Large // 큰 글씨 테마
            else -> R.style.Theme_App_Medium // 기본 테마
        }
    }
    fun setAppTheme(themeResId: Int) {
        val preferences = requireActivity().getSharedPreferences("AppTheme", Context.MODE_PRIVATE)
        with(preferences.edit()) {
            putInt("selected_theme", themeResId)
            apply()
        }
    }
    private fun refreshDialog() {
        parentFragmentManager.beginTransaction().apply {
            detach(this@GroupHomeEditFragmentDialog)
            attach(this@GroupHomeEditFragmentDialog)
            commitAllowingStateLoss()
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
//    private fun adjustViewSizes(view: View, scaleFactor: Float) {
//        val layoutParams = view.layoutParams
//
//        if (layoutParams != null) {
//            //layoutParams.width = (layoutParams.width * scaleFactor).toInt()
//            layoutParams.height = (layoutParams.height * scaleFactor).toInt()
//            if (layoutParams is ViewGroup.MarginLayoutParams) {
//                layoutParams.leftMargin = (layoutParams.leftMargin * scaleFactor).toInt()
//                layoutParams.topMargin = (layoutParams.topMargin * scaleFactor).toInt()
//                layoutParams.rightMargin = (layoutParams.rightMargin * scaleFactor).toInt()
//                layoutParams.bottomMargin = (layoutParams.bottomMargin * scaleFactor).toInt()
//            }
//            view.layoutParams = layoutParams
//        }
//
//        view.setPadding(
//            (view.paddingLeft * scaleFactor).toInt(),
//            (view.paddingTop * scaleFactor).toInt(),
//            (view.paddingRight * scaleFactor).toInt(),
//            (view.paddingBottom * scaleFactor).toInt()
//        )
//
//        if (view is TextView) {
//            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.textSize * scaleFactor)
//        }
//
//        if (view is ViewGroup) {
//            for (child in view.children) {
//                adjustViewSizes(child, scaleFactor)
//            }
//        }
//
//    }
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
