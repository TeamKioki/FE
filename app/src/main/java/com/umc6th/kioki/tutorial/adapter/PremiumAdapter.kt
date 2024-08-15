package com.umc6th.kioki.tutorial.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.tutorial.MenuItem
import com.umc6th.kioki.databinding.KioskIssueRowItemBinding
import com.umc6th.kioki.databinding.PremiumRowItemBinding

class PremiumAdapter(private val onClickListener: (MenuItem) -> Unit) : RecyclerView.Adapter<PremiumAdapter.ViewHolder>() {

    private val premiumItems = mutableListOf<MenuItem>()

    inner class ViewHolder(private val binding: PremiumRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClickListener(premiumItems[adapterPosition])
            }
        }
        fun bind(item: MenuItem) {
            binding.burgerImage.setImageResource(item.imageRes)
            binding.burgerName.text = item.name
            binding.burgerPrice.text = item.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PremiumRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = premiumItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(premiumItems[position])
    }

    fun setList(items: List<MenuItem>) {
        premiumItems.clear()
        premiumItems.addAll(items)
        notifyDataSetChanged()
    }
}