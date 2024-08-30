package com.umc6th.kioki.kiosk

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.databinding.ItemBrandBinding
import com.umc6th.kioki.evaluation.KioskEvaluationActivity

class KioskSearchRVAdapter(private val brandList: MutableList<BrandItem>) : RecyclerView.Adapter<KioskSearchRVAdapter.BrandViewHolder>() {

    inner class BrandViewHolder(private val binding: ItemBrandBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(brandItem: BrandItem) {
            binding.itemBrandnameTv.text = brandItem.brandName
            binding.itemBrandspecTv.text = brandItem.brandSpec
            binding.itemPinnumTv.text = brandItem.pinCount.toString()
            binding.itemDistTv.text = brandItem.distance
            binding.itemBrandlogoIv.setImageResource(brandItem.brandLogoResId)

            // 북마크 상태에 따라 초기 상태 설정
            if (brandItem.isBookmarked) {
                binding.itemBookmarkBtn.visibility = View.VISIBLE
                binding.itemNobookmarkBtn.visibility = View.GONE
            } else {
                binding.itemBookmarkBtn.visibility = View.GONE
                binding.itemNobookmarkBtn.visibility = View.VISIBLE
            }

            // 노북마크 버튼 클릭 이벤트 처리
            binding.itemNobookmarkBtn.setOnClickListener {
                brandItem.isBookmarked = true
                binding.itemBookmarkBtn.visibility = View.VISIBLE
                binding.itemNobookmarkBtn.visibility = View.GONE
            }

            // 북마크 버튼 클릭 이벤트 처리
            binding.itemBookmarkBtn.setOnClickListener {
                brandItem.isBookmarked = false
                binding.itemBookmarkBtn.visibility = View.GONE
                binding.itemNobookmarkBtn.visibility = View.VISIBLE
            }

            // 아이템 클릭 이벤트 처리
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, KioskEvaluationActivity::class.java)

                // 필요하다면 여기에 추가 데이터를 넣을 수 있음 (예: 브랜드 ID, 이름 등)
                intent.putExtra("brandName", brandItem.brandName)
                intent.putExtra("brandSpec", brandItem.brandSpec)
                intent.putExtra("brandLogoResId", brandItem.brandLogoResId)

                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val binding = ItemBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(brandList[position])
    }

    override fun getItemCount(): Int {
        return brandList.size
    }
}