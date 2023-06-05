package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class AllOffersResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val allofferDatList: List<AllOffersItem>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class AllOffersItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String = "",

	@field:SerializedName("url")
	val url: String? = null
)
