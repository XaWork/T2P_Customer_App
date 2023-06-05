package me.taste2plate.app.models.membership.myplan


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MyPlanResponseWithPoints(
    @SerializedName("customer_point")
    val customerPoint: Int,
    @SerializedName("plan")
    val plan: MyPlan?,
    @SerializedName("point_settings")
    val pointSettings: PointSettings
){
    val walletBalance:String
        get() = "₹ ${customerPoint * pointSettings.onePointValueInRupess.toFloat()}"
}