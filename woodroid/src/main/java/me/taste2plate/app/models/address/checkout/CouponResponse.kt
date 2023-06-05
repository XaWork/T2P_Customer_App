package me.taste2plate.app.models.address.checkout

import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.cart.FinalPrice
import me.taste2plate.app.models.cart.GST
import me.taste2plate.app.models.cart.ShippingPrice

data class CouponResponse(
    val cartprice: Float,
    val coupon_discount: Float,
    val coupon_type: String,
    val final_price: Float,
    val message: String,
    val shipping_weight: Float,
    val status: String,
    val total_cgst: Float,
    val total_igst: Float,
    val total_packing_price: Float,
    val total_sgst: Float,
    val gst: GST,
    @SerializedName("gst_with_point")
    val gstWithWallet: GST,
    val shipping: ShippingPrice,
    val new_final_price: FinalPrice
)