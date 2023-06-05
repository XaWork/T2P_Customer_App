package me.taste2plate.app.models.cart


import com.google.gson.annotations.SerializedName

data class Express(
    @SerializedName("total_cgst")
    val totalCgst: String,
    @SerializedName("total_igst")
    val totalIgst: String,
    @SerializedName("total_sgst")
    val totalSgst: String
)