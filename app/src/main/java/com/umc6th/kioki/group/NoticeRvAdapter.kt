package com.umc6th.kioki.group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.R

class NoticeRvAdapter(private val noticeList: List<NoticeEntity>) : RecyclerView.Adapter<NoticeRvAdapter.NoticeViewHolder>() {

    inner class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noticeTitle: TextView = itemView.findViewById(R.id.notice_title)
        val noticeDate: TextView = itemView.findViewById(R.id.notice_date)
        fun bind(notice: NoticeEntity) {
            noticeTitle.text = notice.noticeTitle
            noticeDate.text = notice.noticeDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notice, parent, false)
        return NoticeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }
}