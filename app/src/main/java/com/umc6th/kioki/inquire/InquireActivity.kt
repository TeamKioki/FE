import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.ActivityInquireBinding
import com.umc6th.kioki.inquire.MyInquiriesFragment
import com.umc6th.kioki.inquire.WriteInquireFragment
import com.umc6th.kioki.inquire.adapter.InquireAdapter

class InquireActivity : AppCompatActivity() {

//    // 예: "문의하기" 버튼 클릭 시
//    val intent = Intent(context, InquireActivity::class.java).apply {
//        putExtra(InquireActivity.EXTRA_INITIAL_TAB_INDEX, InquireActivity.TAB_WRITE_INQUIRE)
//    }
//    startActivity(intent)
//
//    // 예: "나의 문의 내역" 버튼 클릭 시
//    val intent = Intent(context, InquireActivity::class.java).apply {
//        putExtra(InquireActivity.EXTRA_INITIAL_TAB_INDEX, InquireActivity.TAB_MY_INQUIRIES)
//    }
//    startActivity(intent)

    private lateinit var binding: ActivityInquireBinding
    private val inquireAdapter: InquireAdapter by lazy { InquireAdapter(this) }
    private val fragments = listOf(
        WriteInquireFragment(),
        MyInquiriesFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inquire)

        // 시스템 바 인셋 처리
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.viewPager.adapter = inquireAdapter
        inquireAdapter.setFragments(fragments)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                TAB_WRITE_INQUIRE -> "문의하기"
                TAB_MY_INQUIRIES -> "나의 문의 내역"
                else -> null
            }
        }.attach()

        // 초기 탭 인덱스 설정
        val initialTabIndex = intent.getIntExtra(EXTRA_INITIAL_TAB_INDEX, TAB_WRITE_INQUIRE)
        binding.viewPager.currentItem = initialTabIndex
    }

    companion object {
        const val EXTRA_INITIAL_TAB_INDEX = "extra_initial_tab_index"
        const val TAB_WRITE_INQUIRE = 0
        const val TAB_MY_INQUIRIES = 1
    }
}
