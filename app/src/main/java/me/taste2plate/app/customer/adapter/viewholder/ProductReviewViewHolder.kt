package me.taste2plate.app.customer.adapter.viewholder

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.toDate
import me.taste2plate.app.customer.utils.DateUtils
import me.taste2plate.app.models.ProductReview
import me.taste2plate.app.models.cart.review.ReviewItem

class ProductReviewViewHolder(val context: Context, itemView: View) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun renderView(review: ReviewItem) {
        val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        val rbRating = itemView.findViewById<RatingBar>(R.id.rbRating)
        val tvMessage = itemView.findViewById<TextView>(R.id.tvMessage)
        tvName.text = review.name
        tvMessage.text = Html.fromHtml(review.review)
        rbRating.rating = review.rating.toFloat()
        tvDate.text = review.createdDate.toDate()
    }


}