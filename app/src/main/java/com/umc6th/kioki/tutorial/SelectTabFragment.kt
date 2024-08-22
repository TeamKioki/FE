package com.umc6th.kioki.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentSelectTabBinding
import com.umc6th.kioki.tutorial.adapter.CartAdapter
import com.umc6th.kioki.tutorial.practice.BurgerKingTutorialActivity
import com.umc6th.kioki.tutorial.tabMenus.AlldayKingFragment
import com.umc6th.kioki.tutorial.tabMenus.ChickenAndShrimpFragment
import com.umc6th.kioki.tutorial.tabMenus.NewProductFragment
import com.umc6th.kioki.tutorial.tabMenus.PremiumFragment
import com.umc6th.kioki.tutorial.tabMenus.SideFragment
import com.umc6th.kioki.tutorial.tabMenus.SpecialPackFragment
import com.umc6th.kioki.tutorial.tabMenus.WhopperFragment
import com.umc6th.kioki.tutorial.tabMenus.dialog.SelectTakeInOrOutDialog
import com.umc6th.kioki.tutorial.tutorial.MainTutorialActivity
import com.umc6th.kioki.utils.TextPrefs
import java.text.NumberFormat
import java.util.Locale

class SelectTabFragment : Fragment() {

    private val tabTitles =
        listOf("스페셜팩", "신제품(new)", "프리미엄", "와퍼&주니어", "치킨&슈림프\n버거", "올데이킹\n&킹모닝", "사이드", "음료&디저트")
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
    private lateinit var binding: FragmentSelectTabBinding
    private val viewModel: TutorialViewModel by activityViewModels()
    private val cardAdapter: CartAdapter by lazy {
        CartAdapter(requireContext()) {
            viewModel.removeSelectedMenu(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (TextPrefs(requireContext()).getTextSize()) {
            binding.apply {
                tab1.tabTitle.textSize = 16f
                tab2.tabTitle.textSize = 16f
                tab3.tabTitle.textSize = 16f
                tab4.tabTitle.textSize = 16f
                tab5.tabTitle.textSize = 16f
                tab6.tabTitle.textSize = 16f
                tab7.tabTitle.textSize = 16f
                tab8.tabTitle.textSize = 16f
                textView14.textSize = 16f
                textView15.textSize = 16f
                cancelButton.textSize = 17f
                payButton.textSize = 17f
            }
        }

        binding.cartRv.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        binding.payButton.setOnClickListener {
            SelectTakeInOrOutDialog().show(parentFragmentManager, "SelectTakeInOrOutDialog")
        }
//
        viewModel.selectedTotalMenu.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.cartRv.visibility = View.GONE
                binding.emptyCartLayout.visibility = View.VISIBLE
            } else {
                binding.cartRv.visibility = View.VISIBLE
                binding.emptyCartLayout.visibility = View.GONE
            }

            cardAdapter.setCartList(it)
            binding.totalPrice.text = it.map { menu ->
                menu.price
            }.sum().let { totalPrice ->
                val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(totalPrice)
                "$formattedPrice 원"
            }
            binding.selectedMenuCount.text = it.size.toString()
        }

        if (binding.tabGridLayout.childCount > 0) {
            val initialTab = binding.tabGridLayout.getChildAt(2) as ConstraintLayout
            val initialTitle: TextView = initialTab.findViewById(R.id.tabTitle)
            val initialIndicator: View = initialTab.findViewById(R.id.tabIndicator)
            selectTab(2, initialTitle, initialIndicator)

            setUpTabs()
        }
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
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.tabContainer, fragment)
        fragmentTransaction.commit()
    }
}