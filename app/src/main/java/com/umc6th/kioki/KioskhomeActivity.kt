package com.umc6th.kioki

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.databinding.ActivityKioskhomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KioskhomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKioskhomeBinding
    private lateinit var adapter: KiohomeBrandlistRVAdapter
    private var isEditMode = false
    private lateinit var userPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKioskhomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = MyApplication.getUser()

        setupEditMode()

        val brandList = mutableListOf(
            Brand("버거킹", "햄버거 · 패스트푸드점", R.drawable.logo_burgerking.toString()),
            Brand("롯데리아", "햄버거 · 패스트푸드점", R.drawable.logo_lotteria.toString()),
            Brand("맘스터치", "햄버거 · 패스트푸드점", R.drawable.logo_momstouch.toString()),
            Brand("KFC", "햄버거 · 패스트푸드점", R.drawable.logo_kfc.toString()),
            Brand("서브웨이", "샌드위치", R.drawable.logo_subway.toString())
        )
        setupRecyclerView(brandList)

        //fetchKioskData()

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

    private fun setupEditMode() {
        Log.d("setupEditMode", "setupEditMode 진입!")
        binding.kiohomeEditBtn.setOnClickListener {
            Log.d("setupEditMode", "편집 버튼 클릭")
            isEditMode = true
            binding.kiohomeEditBtn.visibility = View.GONE
            binding.kiohomePlusBtn.visibility = View.GONE
            binding.kiohomeDoneBtn.visibility = View.VISIBLE
            toggleEditMode()
            Log.d("setupEditMode", "isEditMode : $isEditMode")
            true
        }

        binding.kiohomeDoneBtn.setOnClickListener {
            Log.d("setupEditMode", "완료 버튼 클릭")
            isEditMode = false
            binding.kiohomeEditBtn.visibility = View.VISIBLE
            binding.kiohomePlusBtn.visibility = View.VISIBLE
            binding.kiohomeDoneBtn.visibility = View.GONE
            toggleEditMode()
            Log.d("setupEditMode", "isEditMode : $isEditMode")
        }
    }

    private fun toggleEditMode() {
        adapter.setEditMode(!isEditMode)
    }

    private fun onItemDeleted(position: Int) {
        adapter.deleteItem(position)
    }

//    private fun setupRecyclerView(itemList: List<Brand>) {
//        // RecyclerView의 레이아웃 매니저 설정
//        binding.kiohomeBrandlistRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//
//        // RecyclerView의 어댑터 설정
//        //adapter = KiohomeBrandlistRVAdapter(itemList)
//        //binding.kiohomeBrandlistRv.adapter = adapter
//        val adapter = KiohomeBrandlistRVAdapter(itemList)
//        binding.kiohomeBrandlistRv.adapter = adapter
//    }

    private fun setupRecyclerView(brandList: MutableList<Brand>) {
        adapter = KiohomeBrandlistRVAdapter(brandList, ::onItemDeleted)
        binding.kiohomeBrandlistRv.layoutManager = GridLayoutManager(this, 3)
        binding.kiohomeBrandlistRv.adapter = adapter

        // 드래그 앤 드롭을 위한 ItemTouchHelper 설정
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return if (isEditMode == true) {
                    // 편집 모드에서는 모든 방향에서 드래그 앤 드롭 가능
                    makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0)
                } else {
                    // 편집 모드가 아닐 때는 드래그 앤 드롭 비활성화
                    makeMovementFlags(0, 0)
                }
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // 두 위치의 아이템을 교환
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                adapter.moveItem(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // 스와이프 기능은 사용하지 않음
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.kiohomeBrandlistRv)
    }

//    private fun fetchKioskData() {
//        val token = userPreferences.getString("jwt", "") ?: ""
//        val call = KioskRetrofitObject.getRetrofitService.getKioskList(token)
//        call.enqueue(object : Callback<KioskRetrofitClient2.KioskResponse> {
//            override fun onResponse(call: Call<KioskRetrofitClient2.KioskResponse>, response: Response<KioskRetrofitClient2.KioskResponse>) {
//                if (response.isSuccessful && response.body()?.isSuccess == true) {
//                    val kioskList = response.body()?.result ?: emptyList()
//                    setupRecyclerView(kioskList.map {
//                        Brand(it.name, "레벨: ${it.level}", it.imageUrl)
//                    }.toMutableList())
//                } else {
//                    Log.e("KioskhomeActivity", "API 응답 실패: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<KioskRetrofitClient2.KioskResponse>, t: Throwable) {
//                Log.e("KioskhomeActivity", "API 요청 실패: ${t.message}")
//            }
//        })
//    }
}