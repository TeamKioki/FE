package com.umc6th.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.myapplication.databinding.ItemKioskbrandBinding

class KiohomeBrandlistRVAdapter(private val itemList: List<Brand>) : RecyclerView.Adapter<KiohomeBrandlistRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kioskbrand, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandname: TextView = itemView.findViewById(R.id.item_brandname_tv)
        private val brandinfo: TextView = itemView.findViewById(R.id.dialog_brandinfo_tv)
        private val brandlogo: ImageView = itemView.findViewById(R.id.item_brandlogo_iv)

        fun bind(item: Brand) {
            brandname.text = item.name
            brandlogo.setImageResource(item.logo)

            // 아이템 클릭 이벤트 설정
            itemView.setOnClickListener {
                val context = itemView.context

                // dialog 보여주기
                val dialog = BrandpracticeDialog(
                    context,
                    item.name,
                    item.info,
                    item.logo.toString()
                )
                dialog.show()
            }
        }
    }
}