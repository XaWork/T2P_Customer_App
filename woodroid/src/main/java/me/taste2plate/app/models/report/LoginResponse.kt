package me.taste2plate.app.models.report

import com.google.gson.annotations.SerializedName

data class  LoginResponse(

	@field:SerializedName("token_expires")
	val tokenExpires: String? = null,

	@field:SerializedName("user")
	val user: Any? = null,

	@field:SerializedName("token")
	val token: String? = null
)