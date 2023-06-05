package me.taste2plate.app.customer.models

import com.google.gson.annotations.SerializedName


data class Meta(
	@field:SerializedName("meta_data")
	val metaData: List<MetaDataItem?>? = null
)