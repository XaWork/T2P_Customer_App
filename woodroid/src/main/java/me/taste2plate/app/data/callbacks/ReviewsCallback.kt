package me.taste2plate.app.data.callbacks


import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.ProductReview

import java.util.ArrayList


class ReviewsCallback {
    @SerializedName("product_reviews")
    lateinit var productReviews: ArrayList<ProductReview>
}
