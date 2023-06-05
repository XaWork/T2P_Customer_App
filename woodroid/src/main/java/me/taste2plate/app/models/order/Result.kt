package me.taste2plate.app.models.order

import androidx.annotation.Keep
import me.taste2plate.app.models.address.Address
import java.io.Serializable

@Keep
data class Order(
    val __v: Int,
    val _id: String,
    val additional_cost: String,
    val address: Address?,
    val cargo_partner: Any,
    val cargo_position: Position,
    val coupon: String,
    val couponamount: String,
    val coupontype: String,
    val created_date: String,
    val customer_order_status: String,
    val delivery_boy: Any,
    val delivery_date: String,
    val delivery_partner: Any,
    val delivery_partner_position: Position,
    val express: String,
    val finalprice: String,
    val gateway: String?,
    val orderid: String,
    val otp: String,
    val pickup_boy: Any,
    val pickup_partner: Any,
    val position: Position,
    val price: String,
    val products: List<OrderProductItem>,
    val status: String,
    val timeslot: String,
    val totalCGST: String,
    val totalIGST: String,
    val pickup_weight: String,
    val delivery_weight: String,
    val totalPackingPrice: String,
    val totalSGST: String,
    val totalShippingPrice: String,
    val transactionid: Any,
    val update_date: String,
    val user: User,
    val vendor: Any,
    val vendor_position: Position
):Serializable

fun Order.tCgst() = if(totalCGST.isNotEmpty()) totalCGST else "0"
fun Order.tIGST() = if(totalIGST.isNotEmpty()) totalIGST else "0"
fun Order.tSGST() = if(totalSGST.isNotEmpty()) totalSGST else "0"