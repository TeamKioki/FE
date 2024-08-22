package com.umc6th.kioki

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.databinding.UserItemBinding

class HomeUsersAdapter(private var userList: List<GroupMember>) : RecyclerView.Adapter<HomeUsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GroupMember) {
            //binding.userImgIv.setImageResource(user.profilePictureUrl?) // 이미지 리소스 설정
            binding.userNameTv.text = user.nickname
            binding.userDescriptionTv.text = user.noteTitle
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
        holder.bind(userList[position]) // 해당 위치의 유저 데이터를 바인딩
    }

    fun updateMembers(newMembers: List<GroupMember>) {
        userList = newMembers
        notifyDataSetChanged()
    }
}
