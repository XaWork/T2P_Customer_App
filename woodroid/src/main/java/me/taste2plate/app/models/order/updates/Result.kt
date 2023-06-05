package me.taste2plate.app.models.order.updates
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class Updates(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("order")
    val order: String,
    @SerializedName("__v")
    val v: Int
)
