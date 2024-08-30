package com.umc6th.kioki.kiosk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityKiosksearchBinding

class KioskSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKiosksearchBinding
    private lateinit var filterAdapter: KioskFilterRVAdapter
    private lateinit var brandAdapter: KioskSearchRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKiosksearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 필터 RecyclerView 초기화
        binding.kiosearchFilterRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        filterAdapter = KioskFilterRVAdapter(mutableListOf())
        binding.kiosearchFilterRv.adapter = filterAdapter

        // 브랜드 리스트 RecyclerView 초기화
        binding.kiosearchBrandlistRv.layoutManager = LinearLayoutManager(this)
        brandAdapter = KioskSearchRVAdapter(getSampleBrandItems())
        binding.kiosearchBrandlistRv.adapter = brandAdapter

        goFilter()
        initSpinner()
        goBack()
        goMap()
    }

    // 샘플 브랜드 데이터 생성
    private fun getSampleBrandItems(): MutableList<BrandItem> {
        return mutableListOf(
            BrandItem("버거킹", "햄버거 · 패스트푸드점", R.drawable.logo_burgerking, 106, "300 m"),
            BrandItem("롯데리아", "햄버거 · 패스트푸드점", R.drawable.logo_lotteria, 1207, "500 m"),
            BrandItem("맘스터치", "햄버거 · 패스트푸드점", R.drawable.logo_momstouch, 1313, "1.2 km"),
            BrandItem("KFC", "햄버거 · 패스트푸드점", R.drawable.logo_kfc, 188, "1.4 km"),
            BrandItem("써브웨이", "햄버거 · 패스트푸드점", R.drawable.logo_subway, 1313, "1.4 km"),
        )
    }

    private fun goBack() {
        binding.kiosearchBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun goFilter() {
        binding.kiosearchFilterCl.setOnClickListener {
            val intent = Intent(this, KioskFilterActivity::class.java)
            startActivityForResult(intent, FILTER_REQUEST_CODE)
        }
    }

    private fun goMap() {
        binding.kiosearchMapBtn.setOnClickListener {
            val intent = Intent(this, com.umc6th.kioki.kiosk.KioskMapActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(this, R.array.sort_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.kiosearchSortSp.adapter = adapter
        }

        binding.kiosearchSortSp.onItemSelectedListener =
            object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p0 != null) {
                        //잘 선택되는 지 로그로 확인, 추후 선택된 정렬 방식이 보이도록 재설정 예정
                        Log.d("KioskSearch", p0.getItemAtPosition(p2).toString())
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILTER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedFilters = data?.getStringArrayListExtra("selectedFilters") ?: emptyList()
            updateFilterList(selectedFilters)
        }
    }

    private fun updateFilterList(selectedFilters: List<String>) {
        val filterItems = selectedFilters.map { FilterItem(it) }.toMutableList()
        filterAdapter = KioskFilterRVAdapter(filterItems)
        binding.kiosearchFilterRv.adapter = filterAdapter
        if (filterItems.isNotEmpty()) {
            binding.kiosearchFilterRv.visibility = View.VISIBLE
        } else {
            binding.kiosearchFilterRv.visibility = View.GONE
        }
    }

    companion object {
        private const val FILTER_REQUEST_CODE = 1001
    }
}