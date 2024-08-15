package com.umc6th.kioki.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.umc6th.kioki.tutorial.adapter.TabMenuAdapter
import com.umc6th.kioki.tutorial.tabMenus.AlldayKingFragment
import com.umc6th.kioki.tutorial.tabMenus.ChickenAndShrimpFragment
import com.umc6th.kioki.tutorial.tabMenus.NewProductFragment
import com.umc6th.kioki.tutorial.tabMenus.PremiumFragment
import com.umc6th.kioki.tutorial.tabMenus.SideFragment
import com.umc6th.kioki.tutorial.tabMenus.SpecialPackFragment
import com.umc6th.kioki.tutorial.tabMenus.WhopperFragment
import com.umc6th.myapplication.R
import com.umc6th.myapplication.databinding.ActivityBurgerKingRealBinding

class BurgerKingRealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBurgerKingRealBinding
    private val viewModel: TutorialViewModel by viewModels()
    private val tabMenuAdapter: TabMenuAdapter by lazy { TabMenuAdapter(this) }
    private val tabTitles = listOf("스페셜팩", "신제품(new)", "프리미엄", "와퍼&주니어", "치킨&슈림프\n버거", "올데이킹\n&킹모닝", "사이드", "음료&디저트")
    private val fragments = listOf(
        SpecialPackFragment(),
        NewProductFragment(),
        PremiumFragment(),
        WhopperFragment(),
        ChickenAndShrimpFragment(),
        AlldayKingFragment(),
        SideFragment(),
        SideFragment()
    )

    private var selectedTabIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_burger_king_real)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpTabs()

        if (binding.tabGridLayout.childCount > 0) {
            val initialTab = binding.tabGridLayout.getChildAt(0) as ConstraintLayout
            val initialTitle: TextView = initialTab.findViewById(R.id.tabTitle)
            val initialIndicator: View = initialTab.findViewById(R.id.tabIndicator)
            selectTab(0, initialTitle, initialIndicator)
        }
        startActivity(Intent(this, BurgerKingTutorialActivity::class.java))
    }

    private fun setUpTabs() {
        val tabIds = listOf(
            R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4,
            R.id.tab5, R.id.tab6, R.id.tab7, R.id.tab8
        )

        for (i in tabIds.indices) {
            val tab = binding.tabGridLayout.findViewById<ConstraintLayout>(tabIds[i])
            val tabTitle: TextView = tab.findViewById(R.id.tabTitle)
            val tabIndicator: View = tab.findViewById(R.id.tabIndicator)

            // Set tab title
            tabTitle.text = tabTitles[i]

            tab.setOnClickListener {
                selectTab(i, tabTitle, tabIndicator)
            }
        }
    }

    private fun selectTab(index: Int, tabTitle: TextView, tabIndicator: View) {
        val gridLayout = binding.tabGridLayout

        // Reset previous selected tab
        if (selectedTabIndex != -1) {
            val previousTab = gridLayout.getChildAt(selectedTabIndex) as ConstraintLayout
            val previousTitle: TextView = previousTab.findViewById(R.id.tabTitle)
            val previousIndicator: View = previousTab.findViewById(R.id.tabIndicator)
            previousTitle.setTextColor(resources.getColor(R.color.tab_color))
            previousIndicator.visibility = View.INVISIBLE
        }

        // Set the current tab as selected
        tabTitle.setTextColor(resources.getColor(R.color.tab_selected_color))
        tabIndicator.visibility = View.VISIBLE

        selectedTabIndex = index

        // Replace fragment based on the selected tab
        val fragment = fragments[selectedTabIndex]
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.viewPager, fragment)
        fragmentTransaction.commit()
    }

    private fun observeCartItems() {
        viewModel.selectedTotalMenu.observe(this) {

        }
    }
}