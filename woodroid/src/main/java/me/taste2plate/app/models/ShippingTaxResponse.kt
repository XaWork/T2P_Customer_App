package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class ShippingTaxResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val taxShippingData: TaxShippingData,

    @field:SerializedName("message")
    val message: String? = null
)