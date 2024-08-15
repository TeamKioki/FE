package com.umc6th.kioki.join.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.join.KioskIssue
import com.umc6th.kioki.databinding.KioskIssueRowItemBinding

class KioskIssueAdapter(private val onIssueClick: (KioskIssue) -> Unit) :
    ListAdapter<KioskIssue, KioskIssueAdapter.KioskIssueViewHolder>(DiffUtilCallback()) {

    inner class KioskIssueViewHolder(
        private val binding: KioskIssueRowItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onIssueClick(getItem(adapterPosition))
            }
        }

        fun bind(item: KioskIssue) {
            binding.issue = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KioskIssueViewHolder {
        return KioskIssueViewHolder(
            KioskIssueRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: KioskIssueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffUtilCallback : DiffUtil.ItemCallback<KioskIssue>() {
        override fun areItemsTheSame(oldItem: KioskIssue, newItem: KioskIssue): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: KioskIssue, newItem: KioskIssue): Boolean {
            return oldItem == newItem
        }
    }
}


