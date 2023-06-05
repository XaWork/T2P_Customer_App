package me.taste2plate.app.models.cart

import me.taste2plate.app.models.newproducts.NewProduct
import me.taste2plate.app.models.newproducts.Vendor

data class CartItem(
    val __v: Int,
    var _id: String,
    val brand: String,
    val category: String,
    val city: String,
    val coupon: String,
    val couponamount: String,
    val coupontype: String,
    val created_date: String,
    val cuisine: String,
    var price: String? = "0",
    val product: NewProduct,
    val productname: String,
    var quantity: Int,
    val sub_category: String,
    val update_date: String,
    val user: String,
    val vendor: Vendor
)

fun CartItem.getPriceOrZero():Float = price?.toFloat() ?: 0f