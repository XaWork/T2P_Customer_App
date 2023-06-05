package me.taste2plate.app.models.membership.myplan


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PointSettings(
    @SerializedName("max_redeem_point_per_order")
    val maxRedeemPointPerOrder: String,
    @SerializedName("one_point_value_in_rupess")
    val onePointValueInRupess: String,
    @SerializedName("point_redeem_maximum_order_value")
    val pointRedeemMaximumOrderValue: String,
    @SerializedName("point_redeem_minimum_order_value")
    val pointRedeemMinimumOrderValue: String
)