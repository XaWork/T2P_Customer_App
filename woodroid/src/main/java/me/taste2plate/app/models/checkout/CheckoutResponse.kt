package me.taste2plate.app.models.checkout

import androidx.annotation.Keep

@Keep
data class CheckoutResponse(
    val message: String,
    val orderId: String,
    val status: String
)