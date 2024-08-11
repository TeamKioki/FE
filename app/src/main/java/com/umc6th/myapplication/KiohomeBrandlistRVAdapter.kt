package com.umc6th.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.myapplication.databinding.ItemKioskbrandBinding

class KiohomeBrandlistRVAdapter(private val brandList: ArrayList<Brand>): RecyclerView.Adapter<KiohomeBrandlistRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(album: Brand)
//        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    fun addItem(brand: Brand) {
        brandList.add(brand)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        brandList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): KiohomeBrandlistRVAdapter.ViewHolder {  //ViewHolder 생성 시 호출되는 함수
        val binding: ItemKioskbrandBinding = ItemKioskbrandBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)  //viewHolder의 아이템 객체를 리턴
    }

    override fun onBindViewHolder(holder: KiohomeBrandlistRVAdapter.ViewHolder, position: Int) {  //ViewHolder에 binding을 해줄 때마다 호출되는 함수, 사용자가 스크롤할 때마다 호출됨
        holder.bind(brandList[position])

        //클릭이벤트
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(brandList[position]) }

        //제거
//        holder.binding.itemAlbumTitleTv.setOnClickListener { mItemClickListener.onRemoveAlbum(position) }
    }

    override fun getItemCount(): Int = brandList.size  //데이터셋의 크기를 알려주는 함수, 리사이클러뷰가 마지막이 언제인지를 알려줌

    inner class ViewHolder(val binding: ItemKioskbrandBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(brand: Brand) {
            binding.itemBrandnameTv.text = brand.name
            binding.itemBrandlogoIv.setImageResource(brand.logo!!)
        }
    }

}