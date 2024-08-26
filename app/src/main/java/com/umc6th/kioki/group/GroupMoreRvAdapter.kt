package com.umc6th.kioki.group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.R

class GroupMoreRvAdapter(
    var groupList: MutableList<NotMemberEntity>,
    private val listener: OnMoreGroupClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val group_more_item_img_iv: ImageView = itemView.findViewById(R.id.group_more_item_img_iv)
            val group_more_item_name_tv: TextView = itemView.findViewById(R.id.group_more_item_name_tv)
            val group_more_item_description1_tv: TextView = itemView.findViewById(R.id.group_more_item_description1_tv)
            val group_more_item_description2_tv: TextView = itemView.findViewById(R.id.group_more_item_description2_tv)
            val group_more_item_add_btn_iv: ImageView = itemView.findViewById(R.id.group_more_item_add_btn_iv)
            val group_more_item_check_iv: ImageView = itemView.findViewById(R.id.group_more_item_check_iv)  // 추가

            init {

                group_more_item_add_btn_iv.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(groupList[position])

                    }
                }
            }
        }

    // 추가
    private val differCallback = object : DiffUtil.ItemCallback<NotMemberEntity>() {
        override fun areItemsTheSame(oldItem: NotMemberEntity, newItem: NotMemberEntity): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: NotMemberEntity, newItem: NotMemberEntity): Boolean {
            // User의 내용을 비교해서 같으면 true -> UI 변경 없음
            // User의 내용을 비교해서 다르면 false -> UI 변경
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.home_group_more_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val member: NotMemberEntity = differ.currentList[position]

        if (holder is ViewHolder) {
            holder.group_more_item_name_tv.text = member.name
            holder.group_more_item_description1_tv.text = member.phone
            holder.group_more_item_description2_tv.text = member.introduction
            // isGroupMember 값에 따라 아이콘 자동 설정
            if (member.isGroupMember == false) {
                holder.group_more_item_add_btn_iv.setImageResource(R.drawable.ic_group_add_group) // 그룹에 추가된 상태의 아이콘
            } else {
                holder.group_more_item_add_btn_iv.setImageResource(R.drawable.ic_group_more_check) // 그룹에 추가되지 않은 상태의 아이콘
            }
        }
    }

}