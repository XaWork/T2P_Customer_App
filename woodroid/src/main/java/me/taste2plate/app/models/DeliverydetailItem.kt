package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class DeliverydetailItem(
	@field:SerializedName("delivery_date")
	val deliveryDate: String? = null,

	@field:SerializedName("final_delivery_time")
	val finalDeliveryTime: String? = null,

	@field:SerializedName("delivery_message")
	val deliveryMessage: String? = null,

	@field:SerializedName("cut_offtime")
	val cutOfftime: String? = null
)
