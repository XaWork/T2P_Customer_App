package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class DeliveryBoyDetail(

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("order_id")
	val orderId: String? = null
)