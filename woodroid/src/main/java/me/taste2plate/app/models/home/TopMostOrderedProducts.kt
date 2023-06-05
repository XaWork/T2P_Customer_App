package me.taste2plate.app.models.home

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.cart.review.ReviewItem
import me.taste2plate.app.models.newproducts.NewProduct

data class TopMostOrderedProducts(
    val __v: Int,
    val _id: String,
    val active: Int,
    val createdAt: String,
    val deleted: Int,
    val image: String,
    val products: List<ProductDeal>,
    val slider_name: String,
    val updatedAt: String
)


data class SliderProductsResponse(
    val message: String,
    val result: List<SliderProducts>,
    val status: String
)


data class SliderProducts(
    val __v: Int,
    val _id: String,
    val active: Int,
    val createdAt: String,
    val deleted: Int,
    val image: String,
    val products: List<ProductDeal>,
    val slider_name: String,
    val updatedAt: String
)