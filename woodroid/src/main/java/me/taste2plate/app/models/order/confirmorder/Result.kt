package me.taste2plate.app.models.order.confirmorder

import androidx.annotation.Keep
import me.taste2plate.app.models.order.Position
import me.taste2plate.app.models.order.Product
import java.io.Serializable

@Keep
data class OrderConfirmationData(
    val __v: Int,
    val _id: String,
    val additional_cost: String,
    val address: String,
    val cargo_partner: String,
    val cargo_position: Position,
    val coupon: Any,
    val couponamount: String,
    val coupontype: String,
    val created_date: String,
    val customer_order_status: String,
    val delivery_boy: Any,
    val delivery_date: String,
    val delivery_partner: String,
    val delivery_partner_position: Position,
    val express: String,
    val finalprice: String,
    val gateway: String,
    val orderid: String,
    val otp: String,
    val pickup_boy: Any,
    val pickup_partner: String,
    val position: Position,
    val price: String,
    val products: List<Product>,
    val status: String,
    val timeslot: String,
    val totalCGST: String,
    val totalIGST: String,
    val totalPackingPrice: String,
    val totalSGST: String,
    val totalShippingPrice: String,
    val transactionid: String,
    val update_date: String,
    val user: String,
    val vendor: String,
    val vendor_position: Position
):Serializable

