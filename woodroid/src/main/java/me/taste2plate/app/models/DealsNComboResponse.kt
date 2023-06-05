package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class DealsNComboResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<Product>? = null,

    @field:SerializedName("From-date")
    val fromDate: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("To-date")
    val toDate: String? = null
)

