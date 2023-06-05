package me.taste2plate.app.models.cart


import com.google.gson.annotations.SerializedName

data class Normal(
    @SerializedName("total_cgst")
    var totalCgst: String= "",
    @SerializedName("total_igst")
    var totalIgst: String= "",
    @SerializedName("total_sgst")
    var totalSgst: String = ""
)