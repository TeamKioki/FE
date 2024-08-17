package com.umc6th.kioki

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.RoomDatabase
import com.umc6th.kioki.data.client.RetrofitClient
import com.umc6th.kioki.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var apiService: GroupRetrofitInterface

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

        // 연결할 api 설정
        apiService = RetrofitClient.create(GroupRetrofitInterface::class.java) // baseurl 뒤에 붙일 url이 있는 인터페이스 파일 연결

        // 서버에서 제공받은 Access Token
        val accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGhvbmUiOiIwMTAxMjM0NTY3OCIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MjM3MTU1NTgsImV4cCI6MTcyNjMwNzU1OH0._TI2xGiWqvtNp9ooaf_rRo8puTA1tAZqKoAjADmKwOA"
        // API 호출
        fetchMembersPaged(accessToken, 1)  // 첫 번째 페이지를 가져오기


        //setStartFragment()
        setStartActivity()

        binding.mainDrawerBtnIb.setOnClickListener {
            setExpandableList() // drawerMenu 생성
            binding.mainDrawerLayout.openDrawer(GravityCompat.END) // 드로어 열기
        }

    }

    // 처음 시작하는 프래그먼트 설정하는 함수
//    private fun setStartFragment() {
//        val homeFragment = HomeFragment() // 홈 프래그먼트 생성
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.frame_layout, homeFragment)
//            .commit()
//    }
    private fun fetchMembersPaged(token: String, page: Int) {
        apiService.getMembersPaged("Bearer $token", page).enqueue(object : Callback<GroupMembersPagedResponse> {
            override fun onResponse(call: Call<GroupMembersPagedResponse>, response: Response<GroupMembersPagedResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val result = response.body()
                    Log.d("통신", "Response: $result")
                    // 결과 처리
                } else {
                    Log.e("통신", "Response 실패: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GroupMembersPagedResponse>, t: Throwable) {
                Log.e("통신", "통신 실패: ${t.message}", t)
            }
        })
    }

    private fun setStartActivity() {
        // ViewPager2에 어댑터 설정
        binding.mainUsersVp.adapter = PagerFragmentStateAdapter(this)

        // indicator3를 viewPager2에 연결
        binding.homeUsersIndicator.setViewPager(binding.mainUsersVp)

        // '자세히 보기' 누르면 HomeGroupfragment로 넘어가도록 설정
        binding.mainMoreBtn.setOnClickListener {
            val intent = Intent(this, GroupHomeActivity::class.java)
            startActivity(intent)
            Log.d("클릭", "자세히 보기 클릭")

        }
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