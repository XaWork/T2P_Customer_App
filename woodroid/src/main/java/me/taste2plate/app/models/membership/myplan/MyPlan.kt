package me.taste2plate.app.models.membership.myplan


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MyPlan(
    @SerializedName("cart_max_price")
    val cartMaxPrice: Int,
    @SerializedName("cart_min_price")
    val cartMinPrice: Int,
    @SerializedName("discount_percentage")
    val discountPercentage: Int,
    @SerializedName("exp_date")
    val expDate: String,
    @SerializedName("plan_expired")
    val planExpired: Boolean,
    @SerializedName("plan_name")
    val planName: String,
    @SerializedName("plan_price")
    val planPrice: String
){
    val benefits:String
        get() = buildString {
            if(cartMaxPrice>0) appendLine("\u2022 Max Price : ₹ $cartMaxPrice")
            if(cartMinPrice>0) appendLine("\u2022 Min Price : ₹ $cartMinPrice")
            if(discountPercentage>0) append("\u2022 Discount : $discountPercentage%")
        }

    val itemPrice: String
        get() = "\u20B9 $planPrice"
}