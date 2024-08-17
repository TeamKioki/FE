package com.umc6th.kioki

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class GroupRvAdapter(
    var groupList: MutableList<MemberEntity>,
    private val listener: OnItemClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var showDeleteButton: Boolean = false

    companion object { // 아이템 추가 버튼을 추가하기 위해 나눔
        private const val TYPE_ITEM = 0
        private const val TYPE_ADD_BUTTON = 1
    }

    // DiffUtil을 위한 콜백
    private val differCallback = object : DiffUtil.ItemCallback<GroupMembersResult>() {
        override fun areItemsTheSame(oldItem: GroupMembersResult, newItem: GroupMembersResult): Boolean {
            // GroupMembersResult의 식별자를 비교
            //return oldItem.memberId == newItem.memberId
            return true
        }

        override fun areContentsTheSame(oldItem: GroupMembersResult, newItem: GroupMembersResult): Boolean {
            // GroupMembersResult의 내용을 비교
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    // 각 xml 파일의 id 연결!!
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val group_item_img_iv: ImageView = itemView.findViewById(R.id.group_item_img_iv)
        val group_item_name_tv: TextView = itemView.findViewById(R.id.group_item_name_tv)
        val group_item_description1_tv: TextView = itemView.findViewById(R.id.group_item_description1_tv)
        val group_item_description2_tv: TextView = itemView.findViewById(R.id.group_item_description2_tv)
        val group_item_delete_btn_iv: ImageView = itemView.findViewById(R.id.group_item_delete_btn_iv)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    //listener.onItemClick(differ.currentList[position])
                }
            }
        }
    }

    inner class AddButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val add_button_iv: ImageView = itemView.findViewById(R.id.group_item_add_btn)
    }

    // 어떤 xml파일을 쓸 것인지!!
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) { // 아이템
            val view = LayoutInflater.from(parent.context).inflate(R.layout.home_group_item, parent, false)
            ViewHolder(view)
        } else { // 버튼
            val view = LayoutInflater.from(parent.context).inflate(R.layout.home_group_item_plus, parent, false)
            AddButtonViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size + 1 // 아이템 수 + 추가 버튼
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == differ.currentList.size) TYPE_ADD_BUTTON else TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // val group: Group = groupList[position]
        if (holder is ViewHolder) {
            val member: GroupMembersResult = differ.currentList[position]
            //holder.group_item_img_iv.setImageResource(member.profilePictureUrl!!)
//            holder.group_item_name_tv.text = member.nickname
//            holder.group_item_description1_tv.text = member.noteTitle
//            holder.group_item_description2_tv.text = member.noteText

            holder.group_item_delete_btn_iv.setImageResource(R.drawable.ic_group_subtract)

            holder.group_item_delete_btn_iv.visibility = if (showDeleteButton) {
                View.VISIBLE
            } else {
                View.GONE
            }

            holder.group_item_delete_btn_iv.setOnClickListener {
                removeItem(position)
            }
        } else if (holder is AddButtonViewHolder) {
            holder.add_button_iv.setImageResource(R.drawable.ic_group_plus)

            holder.add_button_iv.setOnClickListener {
                clickAddButton()
            }
        }
    }
    // 추가 버튼 이벤트 -> 눌렀다고 신호 전달만
    private fun clickAddButton() {
        listener.onAddButtonClick()
    }
    // 아이템 삭제 메서드
    private fun removeItem(position: Int) {
        // 리스트에서 해당 아이템 제거
        val mutableList = differ.currentList.toMutableList()
        mutableList.removeAt(position)

        // 업데이트된 리스트를 submit
        differ.submitList(mutableList)
    }

    // 삭제 편집 버튼을 클릭하면 모든 그룹 아이템들의 삭제 아이콘이 뜨도록
    fun toggleDeleteButtonVisibility() {
        showDeleteButton = !showDeleteButton
        Log.d("showDeleteButton", showDeleteButton.toString())
        notifyDataSetChanged() // 전체 목록을 갱신
    }
}