package com.umc6th.kioki

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KiohomeBrandlistRVAdapter(
    private val itemList: MutableList<Brand>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<KiohomeBrandlistRVAdapter.ViewHolder>() {

    private var isEditMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kioskbrand, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.setEditMode(isEditMode)  // Edit Mode 설정
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setEditMode(isEditMode: Boolean) {
        this.isEditMode = !isEditMode
        notifyDataSetChanged()  // RecyclerView를 다시 렌더링하여 trashButton 상태를 업데이트
    }

    fun deleteItem(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val item = itemList.removeAt(fromPosition)
        itemList.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandName: TextView = itemView.findViewById(R.id.item_brandname_tv)
        private val brandLogo: ImageView = itemView.findViewById(R.id.item_brandlogo_iv)
        val trashButton: ImageButton = itemView.findViewById(R.id.item_trash_btn)

        fun bind(item: Brand) {
            brandName.text = item.name
            brandLogo.setImageResource(item.logo.toInt())

            // 기본적으로 trashButton은 숨김
            trashButton.visibility = View.GONE

            // 아이템 클릭 이벤트 설정
            if (!isEditMode) {
                itemView.setOnClickListener {
                    val context = itemView.context

                    // dialog 보여주기
                    val dialog = BrandpracticeDialog(
                        context,
                        item.name,
                        item.info,
                        item.logo.toInt()
                    )
                    dialog.show()
                }
            } else {
                itemView.setOnClickListener {
                    val context = itemView.context

                    // 키오스크 편집 다이얼로그 보여주기
                    val dialog = KioskEditDialog(
                        context,
                        item.name
                        //item.profile.toInt()
                    )
                    dialog.show()
                }
            }

            trashButton.setOnClickListener {
                onDelete(adapterPosition)
            }
        }

        fun setEditMode(isEditMode: Boolean) {
            trashButton.visibility = if (isEditMode) View.VISIBLE else View.GONE
        }
    }

//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val brandname: TextView = itemView.findViewById(R.id.item_brandname_tv)
//        private val brandinfo: TextView = itemView.findViewById(R.id.dialog_brandinfo_tv)
//        private val brandlogo: ImageView = itemView.findViewById(R.id.item_brandlogo_iv)
//        private val brandName: TextView = itemView.findViewById(R.id.item_brandname_tv)
//        private val brandLogo: ImageView = itemView.findViewById(R.id.item_brandlogo_iv)
//        private val trashButton: ImageButton = itemView.findViewById(R.id.item_trash_btn)
//
//
//        fun bind(item: Brand) {
//            if(isEditMode == false) {  // 편집 모드가 아닌 경우
//                brandname.text = item.name
//                brandlogo.setImageResource(item.logo)
//
//                // 아이템 클릭 이벤트 설정
//                itemView.setOnClickListener {
//                    val context = itemView.context
//
//                    // dialog 보여주기
//                    val dialog = BrandpracticeDialog(
//                        context,
//                        item.name,
//                        item.info,
//                        item.logo.toString()
//                    )
//                    dialog.show()
//                }
//            } else {  // 편집 모드인 경우
//                brandName.text = item.name
//                brandLogo.setImageResource(item.logo)
//
//                trashButton.visibility = if (isEditMode) View.VISIBLE else View.GONE
//
//                trashButton.setOnClickListener {
//                    onDelete(adapterPosition)
//                }
//            }
//        }
//    }
}