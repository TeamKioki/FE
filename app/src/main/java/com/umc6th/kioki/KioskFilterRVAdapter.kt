package com.umc6th.kioki

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.databinding.ItemFilterBinding

class KioskFilterRVAdapter(private val filterList: MutableList<FilterItem>) : RecyclerView.Adapter<KioskFilterRVAdapter.FilterViewHolder>() {

    inner class FilterViewHolder(private val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(filterItem: FilterItem, position: Int) {
            binding.itemFilterTv.text = filterItem.filterName

            // 삭제 버튼 클릭 이벤트 처리
            binding.itemCloseBtn.setOnClickListener {
                removeItem(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(filterList[position], position)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    // 아이템 삭제 함수
    private fun removeItem(position: Int) {
        filterList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, filterList.size)
    }
}
