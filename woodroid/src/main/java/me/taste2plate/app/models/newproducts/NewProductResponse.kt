package me.taste2plate.app.models.newproducts

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.cart.review.ReviewItem

@Keep
data class NewProductResponse(
    val message: String,
    @Expose
    @SerializedName("result")
    val result: List<NewProduct>,
    @Expose
    @SerializedName("review")
    val review: List<ReviewItem>,
    val status: String
)