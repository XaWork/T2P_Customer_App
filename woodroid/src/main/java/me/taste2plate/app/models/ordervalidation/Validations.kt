package me.taste2plate.app.models.ordervalidation

import com.google.gson.annotations.SerializedName

data class Validations(

	@SerializedName("maximum_cod_amount")
	val maximumCodAmount: Int? = 0,

	@SerializedName("minimum_order_amount")
	val minimumOrderAmount: Int? = 0,

	@SerializedName("user_cod")
	val user_cod: String? = null,

	@SerializedName("wpud_disable_user_by_admin")
	val wpud_disable_user_by_admin: Int? = null
)
