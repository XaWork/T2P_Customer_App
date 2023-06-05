package me.taste2plate.app.models.offers

import androidx.annotation.Keep

@Keep
data class Coupon(
    val __v: Int,
    val _id: String,
    val active: Int,
    val amount: String,
    val brand: List<Any>,
    val category: List<Any>,
    val coupon: String,
    val created_date: String,
    val customer: List<Any>,
    val deleted: Int,
    val desc: String,
    val exp_date: String,
    val max_usage_limit: Int,
    val max_usage_limit_user: Int,
    val maximum_amount: String,
    val minimum_amount: String,
    val product: List<Any>,
    val start_date: String,
    val type: String,
    val update_date: String
)