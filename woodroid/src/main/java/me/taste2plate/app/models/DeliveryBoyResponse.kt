package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class DeliveryBoyResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val deliveryBoyDetail: DeliveryBoyDetail? = null,

	@field:SerializedName("message")
	val message: String? = null
)