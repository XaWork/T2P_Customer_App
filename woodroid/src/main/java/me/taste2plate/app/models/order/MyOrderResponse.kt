package me.taste2plate.app.models.order

import androidx.annotation.Keep

@Keep
data class MyOrderResponse(
    val count: Int,
    val message: String,
    val result: List<Order>,
    val status: String,
    val server_time: String
)