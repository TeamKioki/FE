package com.umc6th.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.myapplication.databinding.ActivityKioskhomeBinding

class KioskhomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKioskhomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKioskhomeBinding.inflate(layoutInflater)

        val brandList = listOf(
            Brand("버거킹", "햄버거 · 패스트푸드점", R.drawable.logo_burgerking),
            Brand("롯데리아", "햄버거 · 패스트푸드점", R.drawable.logo_lotteria),
            Brand("맘스터치", "햄버거 · 패스트푸드점", R.drawable.logo_momstouch),
            Brand("KFC", "햄버거 · 패스트푸드점", R.drawable.logo_kfc),
            Brand("서브웨이", "샌드위치", R.drawable.logo_subway)
        )
        setupRecyclerView(brandList)

        goBack()
        plusBrand()
    }

    private fun goBack() {
        binding.kiohomeBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun plusBrand() {
        binding.kiohomePlusBtn.setOnClickListener {
            val intent = Intent(this, KioskSearchActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setupRecyclerView(itemList: List<Brand>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.kiohomeBrandlistRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        //adapter = KiohomeBrandlistRVAdapter(itemList)
        //binding.kiohomeBrandlistRv.adapter = adapter
        val adapter = KiohomeBrandlistRVAdapter(itemList)
        binding.kiohomeBrandlistRv.adapter = adapter
    }
}