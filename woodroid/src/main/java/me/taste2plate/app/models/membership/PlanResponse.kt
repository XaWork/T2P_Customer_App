package me.taste2plate.app.models.membership


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PlanResponse(
    @SerializedName("result")
    val plans: List<Plan>,
    @SerializedName("status")
    val status: String
)