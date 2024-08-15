package com.umc6th.kioki

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class GroupMoreRvAdapter (
    var groupList: List<GroupMembersResult>,
    private val listener: OnItemClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val group_more_item_img_iv: ImageView = itemView.findViewById(R.id.group_more_item_img_iv)
            val group_more_item_name_tv: TextView = itemView.findViewById(R.id.group_more_item_name_tv)
            val group_more_item_description1_tv: TextView = itemView.findViewById(R.id.group_more_item_description1_tv)
            val group_more_item_description2_tv: TextView = itemView.findViewById(R.id.group_more_item_description2_tv)
            val group_more_item_add_btn_iv: ImageView = itemView.findViewById(R.id.group_more_item_add_btn_iv)

            init {
                group_more_item_add_btn_iv.setOnClickListener { // 그룹추가 버튼 클릭했을 때 클릭했다고 전달
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(groupList[position])
                    }
                }
            }
        }

    // 추가
    private val differCallback = object : DiffUtil.ItemCallback<MemberEntity>() {
        override fun areItemsTheSame(oldItem: MemberEntity, newItem: MemberEntity): Boolean {
            // User의 id를 비교해서 같으면 areContentsTheSame으로 이동(id 대신 data 클래스에 식별할 수 있는 변수 사용)
            //return oldItem.id == newItem.id
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MemberEntity, newItem: MemberEntity): Boolean {
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
            val member: MemberEntity = differ.currentList[position]

            if (holder is ViewHolder) {
                holder.group_more_item_img_iv.setImageResource(member.memberImg!!)
                holder.group_more_item_name_tv.text = member.memberName
                holder.group_more_item_description1_tv.text = member.memberNoteTitle
                holder.group_more_item_description2_tv.text = member.memberNoteText
            }
        }

}