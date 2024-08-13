package com.umc6th.kioki.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun setFragments(fragments: List<Fragment>) {
        this.fragments.clear()
        this.fragments.addAll(fragments)
        notifyDataSetChanged()
    }

}
