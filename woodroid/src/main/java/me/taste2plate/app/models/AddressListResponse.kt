package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class AddressListResponse(

    @field:SerializedName("code")
	val code: Int? = null,

    @field:SerializedName("data")
	val data: Data? = null,

    @field:SerializedName("message")
	val message: String? = null
)