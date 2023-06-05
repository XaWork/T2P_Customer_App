package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class StateCityZipCodeResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<SCZData>? = null,

	@field:SerializedName("message")
	val message: Any? = null
)

data class CityItem(

	@field:SerializedName("zipcode")
	val zipcode: List<ZipcodeItem>? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class ZipcodeItem(

	@field:SerializedName("code")
	val code: String? = null
)

data class SCZData (

	@field:SerializedName("city")
	val city: List<CityItem>? = null,

	@field:SerializedName("state")
	val state: String? = null
)


