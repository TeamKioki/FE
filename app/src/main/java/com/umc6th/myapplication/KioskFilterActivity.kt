package com.umc6th.myapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.umc6th.myapplication.databinding.ActivityKioskfilterBinding

class KioskFilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKioskfilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKioskfilterBinding.inflate(layoutInflater)

        // 필터 클릭 이벤트 설정
        setFilterClickListeners()

        // 적용하기 버튼 클릭 시 필터링된 리스트를 리사이클러뷰에 적용
        binding.filterContinueCl.setOnClickListener {
            applyFilters()
        }
    }

    private fun setFilterClickListeners() {
        binding.filterEasyTv.setOnClickListener { toggleFilterSelection(it) }
        binding.filterMidscoreTv.setOnClickListener { toggleFilterSelection(it) }
        binding.filterDifTv.setOnClickListener { toggleFilterSelection(it) }
        binding.filterOneTv.setOnClickListener { toggleFilterSelection(it) }
        binding.filterTwomoreTv.setOnClickListener { toggleFilterSelection(it) }
        binding.filterShortTv.setOnClickListener { toggleFilterSelection(it) }
        binding.filterMiddistTv.setOnClickListener { toggleFilterSelection(it) }
        binding.filterLongTv.setOnClickListener { toggleFilterSelection(it) }
    }

    private fun toggleFilterSelection(view: View) {
        val textView = view as TextView
        val isSelected = textView.background.constantState == ContextCompat.getDrawable(this, R.drawable.background_orange)?.constantState

        if (isSelected) {
            textView.background = ContextCompat.getDrawable(this, R.drawable.background_lightgray_50dp)
            textView.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else {
            textView.background = ContextCompat.getDrawable(this, R.drawable.background_orange)
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun applyFilters() {
        // 선택된 필터에 따라 필터링 로직을 구현하세요.
        // 예시: filterSelectedBrands 메서드에서 필터 조건에 맞는 브랜드 리스트를 필터링하여 리턴
        val filteredBrands = filterSelectedBrands()

        // 필터링된 리스트를 리사이클러뷰에 적용
        val adapter = KiohomeBrandlistRVAdapter(filteredBrands)
        // RecyclerView 설정
        // 예시: recyclerView.adapter = adapter
    }

    private fun filterSelectedBrands(): List<Brand> {
        // 브랜드 리스트를 필터링하는 로직 구현
        // 예를 들어, "쉬움" 필터가 선택되었을 때 쉬운 난이도의 브랜드만 필터링
        val selectedFilters = mutableListOf<String>()

        if (binding.filterEasyTv.background.constantState == ContextCompat.getDrawable(this, R.drawable.background_orange)?.constantState) {
            selectedFilters.add("쉬움")
        }
        // 나머지 필터에 대해서도 동일하게 처리

        // 필터 조건에 맞게 브랜드 리스트 필터링 (brandList는 전체 브랜드 리스트)
        return brandList.filter { brand ->
            // 필터 조건에 맞는지 검사 (예시)
            selectedFilters.contains(brand.difficulty)
        }
    }
}