package com.umc6th.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.myapplication.databinding.ActivityKioskhomeBinding

class KioskhomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKioskhomeBinding
    private lateinit var adapter: KiohomeBrandlistRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKioskhomeBinding.inflate(layoutInflater)

        val brand1 = Brand("버거킹", R.drawable.logo_burgerking)
        val brand2 = Brand("롯데리아", R.drawable.logo_lotteria)
        val brand3 = Brand("맘스터치", R.drawable.logo_momstouch)
        val brand4 = Brand("KFC", R.drawable.logo_kfc)
        val brand5 = Brand("서브웨이", R.drawable.logo_subway)

        val itemList = listOf(brand1, brand2, brand3, brand4, brand5)
        //setupRecyclerView(itemList)

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


    private fun setupRecyclerView(itemList: List<String>) {
        // RecyclerView의 레이아웃 매니저 설정
        //binding.kiohomeBrandlistRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        //adapter = KiohomeBrandlistRVAdapter(itemList)
        //binding.kiohomeBrandlistRv.adapter = adapter
    }
}