package com.umc6th.kioki

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.databinding.UserItemBinding

class HomeUsersAdapter(private val userList : List<Group>) : RecyclerView.Adapter<HomeUsersAdapter.UserViewHolder>(){
    inner class UserViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: Group){
            user.groupImg?.let { binding.userImgIv.setImageResource(it) }
            binding.userNameTv.text = user.groupName
            binding.userDescriptionTv.text = user.groupDescription1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }


}