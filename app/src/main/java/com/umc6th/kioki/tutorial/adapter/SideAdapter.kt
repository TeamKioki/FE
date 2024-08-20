package com.umc6th.kioki.tutorial.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.tutorial.SideMenu
import com.umc6th.kioki.databinding.SideRowItemBinding

class SideAdapter(private val onClickListener: (Int) -> Unit) : RecyclerView.Adapter<SideAdapter.ViewHolder>() {

    private val items = mutableListOf<SideMenu>()

    inner class ViewHolder(private val binding: SideRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClickListener(adapterPosition)
            }
        }
        fun bind(item: SideMenu) {
            if (item.isSelected) {
                binding.checked.visibility = android.view.View.VISIBLE
            } else {
                binding.checked.visibility = android.view.View.GONE
            }
            binding.sideName.text = item.name
            binding.sideImage.setImageResource(item.imageRes)
//            binding.sideName.text = item.name
            binding.sidePrice.text = "+ ${item.price}원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SideRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setSideList(items: List<SideMenu>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}