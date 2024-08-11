package com.umc6th.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.umc6th.myapplication.databinding.ActivityKiosksearchBinding

class KioskSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKiosksearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKiosksearchBinding.inflate(layoutInflater)

        showFilter()
        initSpinner()
        goBack()
    }

    private fun goBack() {
        binding.kiosearchBackBtn.setOnClickListener {
            finish()
        }
    }

    private fun showFilter() {
        binding.kiosearchFilterCl.setOnClickListener {
            val intent = Intent(this, KioskFilterActivity::class.java)
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
                        //잘 선택되는 지 토스트 메세지로 확인, 추후 선택된 정렬 방식이 보이도록 재설정 예정
                        Toast.makeText(this@KioskSearchActivity, p0.getItemAtPosition(p2).toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    return
                }
            }
    }
}