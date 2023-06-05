package me.taste2plate.app.models.order.confirmorder

import java.io.Serializable


data class OrderConfirmationResponse(
    val message: String,
    val result: OrderConfirmationData,
    val status: String
):Serializable