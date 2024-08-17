package com.umc6th.kioki

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // 레이아웃 설정
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //setStartFragment()
        setStartActivity()
        // ViewPager2에 어댑터 설정
        binding.mainUsersVp.adapter = PagerFragmentStateAdapter(this)

        // indicator3를 viewPager2에 연결
        binding.homeUsersIndicator.setViewPager(binding.mainUsersVp)

        binding.mainDrawerBtnIb.setOnClickListener {
            setExpandableList() // drawerMenu 생성
            binding.mainDrawerLayout.openDrawer(GravityCompat.END) // 드로어 열기
        }

    }

    private fun updateViewPager(members: List<GroupMember>) {
        val adapter = binding.mainUsersVp.adapter as PagerFragmentStateAdapter
        //adapter.updateMembers(members)
        binding.homeUsersIndicator.setViewPager(binding.mainUsersVp)  // Indicator 업데이트
    }

    private fun setStartActivity() {
        // '자세히 보기' 누르면 HomeGroupfragment로 넘어가도록 설정
        binding.mainMoreBtn.setOnClickListener {
            val intent = Intent(this, GroupHomeActivity::class.java)
            startActivity(intent)
            Log.d("클릭", "자세히 보기 클릭")

        }
    }

    private fun setUpViewPager(memberList: List<GroupMember>) {
        // ViewPager2에 어댑터 설정
        binding.mainUsersVp.adapter = PagerFragmentStateAdapter(this, memberList)

        // indicator3를 viewPager2에 연결
        binding.homeUsersIndicator.setViewPager(binding.mainUsersVp)

    }

    private fun setExpandableList() {
        val parentList = mutableListOf("공지사항", "계정관리", "문의하기", "고객센터") // 부모 리스트
        val childList = mutableListOf(
            mutableListOf(),
            mutableListOf("계정 편집", "알림 설정", "계정 탈퇴"),
            mutableListOf(),
            mutableListOf()
        )
        val expandableAdapter = MainExpandableListAdapter(this, parentList, childList)
        val expandableList = findViewById<ExpandableListView>(R.id.main_menu_el)
        expandableList.setAdapter(expandableAdapter)


        findViewById<ExpandableListView>(R.id.main_menu_el).setOnGroupClickListener { parent, v, groupPosition, id ->
            /* todo : parent 클릭 이벤트 설정 */
            false
        }
        findViewById<ExpandableListView>(R.id.main_menu_el).setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            /* todo : child 클릭 이벤트 설정 */
            false
        }
    }
}