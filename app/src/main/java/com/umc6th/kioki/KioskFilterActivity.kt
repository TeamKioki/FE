package com.umc6th.kioki

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.umc6th.kioki.databinding.ActivityKioskfilterBinding

class KioskFilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKioskfilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKioskfilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 필터 클릭 이벤트 설정
        setFilterClickListeners()

        // 적용하기 버튼 클릭 시 필터링된 리스트를 리사이클러뷰에 적용
        binding.filterContinueCl.setOnClickListener {
            applyFilters()
            finish()
        }

        binding.filterCloseBtn.setOnClickListener {
            finish()
        }

        binding.filterRetryBtn.setOnClickListener {
            resetFilters()
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
        val isSelected = textView.background.constantState == ContextCompat.getDrawable(this,
            R.drawable.btn_group_edit_rounded_orange
        )?.constantState

        if (isSelected) {
            textView.background = ContextCompat.getDrawable(this,
                R.drawable.background_lightgray_50dp
            )
            textView.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else {
            textView.background = ContextCompat.getDrawable(this, R.drawable.btn_group_edit_rounded_orange)
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun applyFilters() {
        val selectedFilters = mutableListOf<String>()

        if (isFilterSelected(binding.filterEasyTv)) selectedFilters.add("쉬움")
        if (isFilterSelected(binding.filterMidscoreTv)) selectedFilters.add("중간")
        if (isFilterSelected(binding.filterDifTv)) selectedFilters.add("어려움")
        if (isFilterSelected(binding.filterOneTv)) selectedFilters.add("1개")
        if (isFilterSelected(binding.filterTwomoreTv)) selectedFilters.add("2개 이상")
        if (isFilterSelected(binding.filterShortTv)) selectedFilters.add("0.5km 이내")
        if (isFilterSelected(binding.filterMiddistTv)) selectedFilters.add("1.0km 이내")
        if (isFilterSelected(binding.filterLongTv)) selectedFilters.add("1.5km 이내")

        val intent = Intent()
        intent.putStringArrayListExtra("selectedFilters", ArrayList(selectedFilters))
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun isFilterSelected(textView: TextView): Boolean {
        return textView.background.constantState == ContextCompat.getDrawable(this, R.drawable.btn_group_edit_rounded_orange)?.constantState
    }

    private fun resetFilter(textView: TextView) {
        textView.background = ContextCompat.getDrawable(this,
            R.drawable.background_lightgray_50dp
        )
        textView.setTextColor(ContextCompat.getColor(this, R.color.black))
    }

    private fun resetFilters() {
        // 모든 필터 버튼을 기본 상태로 되돌림
        resetFilter(binding.filterEasyTv)
        resetFilter(binding.filterMidscoreTv)
        resetFilter(binding.filterDifTv)
        resetFilter(binding.filterOneTv)
        resetFilter(binding.filterTwomoreTv)
        resetFilter(binding.filterShortTv)
        resetFilter(binding.filterMiddistTv)
        resetFilter(binding.filterLongTv)
    }

//    private fun filterSelectedBrands(): List<Brand> {
//        // 브랜드 리스트를 필터링하는 로직 구현
//        // 예를 들어, "쉬움" 필터가 선택되었을 때 쉬운 난이도의 브랜드만 필터링
//        val selectedFilters = mutableListOf<String>()
//
//        if (binding.filterEasyTv.background.constantState == ContextCompat.getDrawable(this, R.drawable.background_orange)?.constantState) {
//            selectedFilters.add("쉬움")
//        }
//        // 나머지 필터에 대해서도 동일하게 처리
//
//        // 필터 조건에 맞게 브랜드 리스트 필터링 (brandList는 전체 브랜드 리스트)
//        return brandList.filter { brand ->
//            // 필터 조건에 맞는지 검사 (예시)
//            selectedFilters.contains(brand.difficulty)
//        }
//    }
}