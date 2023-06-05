package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("addresses")
	val addresses: ArrayList<UserAddress>? = null,

	@field:SerializedName("user_id")
	val userId: String? = null
)