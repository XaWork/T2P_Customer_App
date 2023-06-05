package me.taste2plate.app.models.cart

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.newproducts.Shipping
import java.text.SimpleDateFormat
import java.util.*

@Keep
data class CartItemResponse(
    val cartprice: Float,
    val total_price: Float,
    val total_packing_price: Float,
    val final_price: Float,
    val shipping_weight: Float,
    val result: List<CartItem>,
    val status: String,
    val normal_delivery_date: String,
    val gst: GST,
    @SerializedName("gst_with_point")
    val gstWithPoint: GST,
    val shipping:ShippingPrice,
    val new_final_price:FinalPrice,
    val plan_discount:Float,
    val customer_point:Float,
    val one_point_value:Float,
    @SerializedName("max_open_cod_order")
    val maxCodOrderValue:Float,
    @SerializedName("open_order_value")
    val openOrderValue:Float,
){
    val walletDiscount : String
        get() = "Rs. ${customer_point * one_point_value}"
}

@Keep
data class ShippingPrice(val normal_shipping:String, val express_shipping:String)

@Keep
data class FinalPrice(val normal:String, val express:String, @SerializedName("with_wallet") val withWallet:FinalPrice?)

fun CartItemResponse.deliveryDate() : Date {
    val simpleDateFormatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return simpleDateFormatter.parse(normal_delivery_date)!!
}

