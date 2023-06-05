package me.taste2plate.app.models.order.updates


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OrderUpdateResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val updates: List<Updates>,
    @SerializedName("status")
    val status: String
)

