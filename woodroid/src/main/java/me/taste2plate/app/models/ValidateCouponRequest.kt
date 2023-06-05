package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class ValidateCouponRequest(

	@field:SerializedName("order_data")
	val orderData: CartData? = null
)

data class CartArrItem(

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("product_id")
	val productId: String? = null
)

data class CartData(

	@field:SerializedName("coupon_id")
	val couponId: String? = null,

	@field:SerializedName("cart_arr")
	val cartArr: List<CartArrItem>? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null
)
