package com.umc6th.kioki.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.databinding.UserItemBinding

class HomeUsersAdapter(private var userList: List<MemberEntity>) : RecyclerView.Adapter<HomeUsersAdapter.UserViewHolder>() {

    private var selectedItemPosition: Int = 0

    inner class UserViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: MemberEntity, isSelected: Boolean) {
            // Set user data
            binding.userNameTv.text = user.nickname
            binding.userDescriptionTv.text = user.noteTitle

            itemView.isSelected = isSelected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position], position == selectedItemPosition)

        holder.itemView.setOnClickListener {
            val previousItemPosition = selectedItemPosition
            val currentPosition = position

            // Update the selected item position
            selectedItemPosition = currentPosition

            // Notify the adapter to refresh the previously selected item and the currently selected item
            notifyItemChanged(previousItemPosition)
            notifyItemChanged(currentPosition)
        }
    }


//        holder.itemView.setOnClickListener {
//            val previousItemPosition = selectedItemPosition
//            selectedItemPosition = position
//
//            // Notify the adapter of item changes
//            notifyItemChanged(previousItemPosition)
//            notifyItemChanged(position)
//        }
//    }

    fun updateMembers(newMembers: List<MemberEntity>) {
        userList = newMembers
        notifyDataSetChanged()
    }
}
