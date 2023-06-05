package me.taste2plate.app.customer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.adapter.viewholder.ProductReviewViewHolder
import me.taste2plate.app.models.ProductReview
import me.taste2plate.app.models.cart.review.ReviewItem

class ProductReviewAdapter(private val reviews: List<ReviewItem>) : androidx.recyclerview.widget.RecyclerView.Adapter<ProductReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductReviewViewHolder {
        return ProductReviewViewHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.single_product_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductReviewViewHolder, position: Int) {
        holder.renderView(reviews[position])
    }


    override fun getItemCount(): Int {
        return if (reviews.isEmpty()) 0 else reviews.size
    }
}
