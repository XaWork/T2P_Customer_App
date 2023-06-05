package me.taste2plate.app.customer.models

import com.google.gson.annotations.SerializedName

data class MetaDataItem(

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("key")
	val key: String? = null
)