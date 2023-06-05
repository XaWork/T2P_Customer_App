package me.taste2plate.app.models.wallet


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Transaction(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("point")
    val point: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("update_date")
    val updateDate: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("__v")
    val v: Int
)