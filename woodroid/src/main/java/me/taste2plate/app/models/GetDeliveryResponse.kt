package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class GetDeliveryResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val deliverydetail: List<DeliverydetailItem?>? = null,

    @field:SerializedName("message")
    val message: String? = null
)
