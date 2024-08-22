package com.umc6th.kioki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc6th.kioki.databinding.ActivityNavAccountEditBinding

class NavAccountEditActivity : AppCompatActivity() {
    lateinit var binding : ActivityNavAccountEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavAccountEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.groupHeaderNavBackIv.setOnClickListener {
            finish()
        }
        binding.navKioskSettingBtnIv.setOnClickListener {
            val dialog = KioskSettingFragmentDialog()
            var bundle = Bundle() // 다이얼로그에 전달할 Bundle 생성
//            bundle.putInt("MemberId", member.memberId!!)
//            bundle.putString("MemberName", member.nickname)
//            bundle.putString("MemberNoteTitle", member.noteTitle)
//            bundle.putString("MemberNoteText", member.noteText)
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, "KioskSettingFragmentDialog")

        }
    }
}