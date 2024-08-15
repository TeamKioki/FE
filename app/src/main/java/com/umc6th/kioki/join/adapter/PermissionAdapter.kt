package com.umc6th.kioki.join.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.join.PermissionItem
import com.umc6th.myapplication.databinding.PermissionRowItemBinding

class PermissionAdapter :  RecyclerView.Adapter<PermissionAdapter.ViewHolder>() {
    private val permissionItems = mutableListOf<PermissionItem>()
    inner class ViewHolder(private val binding: PermissionRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PermissionItem) {
            binding.permissionItem = item
            binding.permissionImage.setImageResource(item.imageRes)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PermissionAdapter.ViewHolder {
        return ViewHolder(
            PermissionRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PermissionAdapter.ViewHolder, position: Int) {
        holder.bind(permissionItems[position])
    }

    override fun getItemCount(): Int = permissionItems.size

    fun setList(items: List<PermissionItem>) {
        permissionItems.clear()
        permissionItems.addAll(items)
        notifyDataSetChanged()

    }
}