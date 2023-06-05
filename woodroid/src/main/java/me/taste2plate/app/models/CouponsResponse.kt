package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class CouponsResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("data")
	val couponData: ArrayList<CouponData>? = null
)

data class CouponData(

	@field:SerializedName("coupon_amount")
	val couponAmount: Int = 0,

	@field:SerializedName("usage_limit")
	val usageLimit: String? = null,

	@field:SerializedName("usage_count")
	val usageCount: String? = null,

	@field:SerializedName("product_ids")
	val productIds: List<String?>? = null,

	@field:SerializedName("usage_limit_per_user")
	val usageLimitPerUser: String? = null,

	@field:SerializedName("date_expires")
	val dateExpires: String? = null,

	@field:SerializedName("maximum_amount")
	val maximumAmount: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("discount_type")
	val discountType: String? = null,

	@field:SerializedName("minimum_amount")
	val minimumAmount: String? = null,

	@field:SerializedName("coupon_name")
	val couponName: String? = null,

	@field:SerializedName("coupon_description")
	val couponDescription: String? = null
)
