package me.taste2plate.app.models.cart.review


import com.google.gson.annotations.SerializedName

data class ReviewItem(
    @SerializedName("active")
    val active: Int,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted")
    val deleted: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("product")
    val product: String,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("review")
    val review: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("__v")
    val v: Int
)