package com.umc6th.kioki

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class GroupListAdapter(var groupList: List<Group>): RecyclerView.Adapter<GroupListAdapter.ViewHolder>() {
    var showDeleteButton: Boolean = false
    // 추가
    private val differCallback = object : DiffUtil.ItemCallback<Group>() {
        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
            // User의 id를 비교해서 같으면 areContentsTheSame으로 이동(id 대신 data 클래스에 식별할 수 있는 변수 사용)
            //return oldItem.id == newItem.id
            return false
        }

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
            // User의 내용을 비교해서 같으면 true -> UI 변경 없음
            // User의 내용을 비교해서 다르면 false -> UI 변경
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        lateinit var group_item_img_iv: ImageView
        lateinit var group_item_name_tv: TextView
        lateinit var group_item_description1_tv: TextView
        lateinit var group_item_description2_tv: TextView

        init {
            group_item_img_iv = itemView.findViewById(R.id.group_item_img_iv)
            group_item_name_tv = itemView.findViewById(R.id.group_item_name_tv)
            group_item_description1_tv = itemView.findViewById(R.id.group_item_description1_tv)
            group_item_description2_tv = itemView.findViewById(R.id.group_item_description2_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_group_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // val group: Group = groupList[position]
        val group : Group = differ.currentList[position]

        holder.group_item_img_iv.setImageResource(group.groupImg!!)
        holder.group_item_name_tv.text = group.groupName
        holder.group_item_description1_tv.text = group.groupDescription1
        holder.group_item_description2_tv.text = group.groupDescription2

    }
    // 삭제 편집 버튼을 클릭하면 모든 그룹 아이템들의 삭제 아이콘이 뜨도록
    fun toggleDeleteButtonVisibility() {

    }
}