package com.umc6th.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.umc6th.kioki.HomeGroupFragment
import com.umc6th.myapplication.databinding.FragmentHomeBinding

class HomeFragment:Fragment() {
    private lateinit var binding : FragmentHomeBinding // 뷰 바인딩

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // ViewPager2에 어댑터 설정
        binding.homeUsersVp.adapter = PagerFragmentStateAdapter(requireActivity())

        // indicator3를 viewPager2에 연결
        binding.homeUsersIndicator.setViewPager(binding.homeUsersVp)

        // '자세히 보기' 누르면 HomeGroupfragment로 넘어가도록 설정
        binding.homeMoreBtnTv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, HomeGroupFragment())
                .addToBackStack(null) // 뒤로 가기 시 이전 프래그먼트로 돌아오기 위해 백스택에 추가
                .commit()
        }

        return binding.root
    }
}