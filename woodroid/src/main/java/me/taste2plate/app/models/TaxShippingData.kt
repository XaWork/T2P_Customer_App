package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class TaxShippingData(

	@field:SerializedName("igst_total")
	val igstTotal: Double? = null,

	@field:SerializedName("packaging_rate")
	val packagingRate: Double? = null,

	@field:SerializedName("cgst_total")
	val cgstTotal: Double? = null,

	@field:SerializedName("total_shipping")
	val totalShipping: Int? = null,

	@field:SerializedName("sgst_total")
	val sgstTotal: Double? = null,

	@field:SerializedName("gst_apply")
	val gstApply: String? = null,

	@field:SerializedName("service_available")
	val isServiceAvailable: Int = 0,

	@field:SerializedName("dod")
    val deliveryDate: Int = 0

)