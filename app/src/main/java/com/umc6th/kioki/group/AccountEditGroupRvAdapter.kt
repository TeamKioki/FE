package com.umc6th.kioki.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.databinding.UserItemBinding

class AccountEditGroupRvAdapter(private var userList: List<MemberEntity>) : RecyclerView.Adapter<AccountEditGroupRvAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: MemberEntity) {
            binding.userNameTv.text = user.nickname
            binding.userDescriptionTv.text = user.noteTitle
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        return UserViewHolder(
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }
}
