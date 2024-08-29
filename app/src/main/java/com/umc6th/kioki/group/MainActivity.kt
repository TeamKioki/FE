package com.umc6th.kioki.group

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.umc6th.kioki.KioskhomeActivity
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityMainBinding
import com.umc6th.kioki.inquire.InquireActivity
import com.umc6th.kioki.unregister.UnregisterActivity


class MainActivity : AppCompatActivity(), OnGroupMemberChangeListener {
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

        setUpViewPager()
//        // ViewPager2에 어댑터 설정
//        binding.mainUsersVp.adapter = PagerFragmentStateAdapter(this)
//
//        // indicator3를 viewPager2에 연결
//        binding.homeUsersIndicator.setViewPager(binding.mainUsersVp)

        binding.mainDrawerBtnIb.setOnClickListener {
            setExpandableList() // drawerMenu 생성
            binding.mainDrawerLayout.openDrawer(GravityCompat.END) // 드로어 열기
        }

        val headerView = binding.navigationView.getHeaderView(0)
        val drawerCancelIv = headerView.findViewById<ImageView>(R.id.drawer_cancel_iv)
        drawerCancelIv.setOnClickListener {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.END) // 드로어 닫기
        }

        binding.mainGotoBtn.setOnClickListener{
            val intent = Intent(this, KioskhomeActivity::class.java)
            startActivity(intent)
        }

        // 공지사항
        binding.mainCommentContainerIv.setOnClickListener {
            val intent = Intent(this, NoticeActivity::class.java)
            startActivity(intent)
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

    private fun setUpViewPager() {
        val filteredMembers = MemberLists.members.filter { it.isGroupMember == true }
        binding.mainUsersVp.adapter = PagerFragmentStateAdapter(this, filteredMembers)

        binding.homeUsersIndicator.setViewPager(binding.mainUsersVp)
    }

//    private fun setUpViewPager(memberList: List<GroupMember>) {
//        // ViewPager2에 어댑터 설정
//        binding.mainUsersVp.adapter = PagerFragmentStateAdapter(this, memberList)
//
//        // indicator3를 viewPager2에 연결
//        binding.homeUsersIndicator.setViewPager(binding.mainUsersVp)
//    }

    private fun setExpandableList() {
        val parentList = mutableListOf("공지사항", "계정관리", "문의하기", "고객센터") // 부모 리스트
        val childList = mutableListOf(
            mutableListOf(),
            mutableListOf("계정 편집", "알림 설정", "계정 탈퇴"),
            mutableListOf("자주 묻는 질문 (FAQ)", "문의하기 (Q&A)", "키오스크 등록 요청"),
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
            val activity = when (groupPosition) {
                1 -> when (childPosition) {
                    0 -> NavAccountEditActivity::class.java // 계정 편집
                    1 -> null // 알림 설정
                    2 -> UnregisterActivity::class.java // 계정 탈퇴
                    else -> null
                }
                2 -> when (childPosition) {
                    0 ->  InquireActivity::class.java// 자주 묻는 질문 (FAQ)
                    else -> InquireActivity::class.java// 키오스크 등록 요청
                }
                else -> null
            }
            Log.d("그룹", childPosition.toString())
            val intent = Intent(this, activity)
            startActivity(intent)

            false
        }
    }

    override fun onGroupMemberChanged() {
        setUpViewPager()
        Log.d("그룹", "눌러짐")
    }
}