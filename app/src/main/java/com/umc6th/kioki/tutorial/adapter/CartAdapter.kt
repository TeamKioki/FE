package com.umc6th.kioki.tutorial.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.databinding.CartColumnItemBinding
import com.umc6th.kioki.databinding.SideRowItemBinding
import com.umc6th.kioki.tutorial.MenuItem
import com.umc6th.kioki.utils.TextPrefs
import com.umc6th.kioki.utils.TokenPrefs

class CartAdapter(private val context: Context, private val onDeleteClickListener: (Int) -> Unit) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private val cartItems = mutableListOf<MenuItem>()
    inner class ViewHolder(private val binding: CartColumnItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.deleteButton.setOnClickListener {
                onDeleteClickListener(adapterPosition)
            }
        }
        fun bind(menuItem: MenuItem) {
            if (TextPrefs(context).getTextSize()) {
                binding.menuName.textSize = 15f
                binding.menuPrice.textSize = 15f
            }
            binding.menuName.text = menuItem.name
            binding.menuPrice.text = menuItem.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CartColumnItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    fun setCartList(it: List<MenuItem>)
    {
        this.cartItems.clear()
        this.cartItems.addAll(it)
        notifyDataSetChanged()
    }
}