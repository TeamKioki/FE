package com.umc6th.kioki.group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umc6th.kioki.R

class ReviewRvAdapter(private val reviewList: List<ReviewEntity>) : RecyclerView.Adapter<ReviewRvAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val restaurantImg: ImageView = itemView.findViewById(R.id.review_restaurant_img_iv)
        val restaurantName: TextView = itemView.findViewById(R.id.review_restaurant_name_tv)
        val footType: TextView = itemView.findViewById(R.id.review_food_type_tv)
        val reviewContent: TextView = itemView.findViewById(R.id.review_content_tv)
        val reviewRating: TextView = itemView.findViewById(R.id.review_rating_tv)
        val reviewRatingIv: ImageView = itemView.findViewById(R.id.review_rating_iv)
        fun bind(review: ReviewEntity) {
            restaurantImg.setImageResource(review.restaurantImg!!)
            restaurantName.text = review.restaurantName
            footType.text = review.foodType
            reviewContent.text = review.reviewContent
            reviewRating.text = review.rating.toString()
            if(reviewRating.text == "5.0") {
                reviewRatingIv.setImageResource(R.drawable.ic_rating_5)
            } else if(reviewRating.text == "4.0") {
                reviewRatingIv.setImageResource(R.drawable.ic_rating_4)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kiosk_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviewList[position])
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }
}