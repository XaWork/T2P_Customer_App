package me.taste2plate.app.models.cart


import com.google.gson.annotations.SerializedName

data class GST(
    @SerializedName("express")
    val express: Express,
    @SerializedName("normal")
    val normal: Normal
)