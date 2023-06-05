package me.taste2plate.app.models.order

import androidx.annotation.Keep
import java.io.Serializable


@Keep
data class Product(
    val __v: Int,
    val _id: String,
    val brand: String,
    val category: String,
    val city: String,
    val coupon: String,
    val couponamount: String,
    val coupontype: String,
    val created_date: String,
    val cuisine: String,
    val price: Int,
    val product: String,
    val productname: String,
    val quantity: Int,
    val sub_category: String,
    val update_date: String,
    val user: String,
    val vendor: String
): Serializable