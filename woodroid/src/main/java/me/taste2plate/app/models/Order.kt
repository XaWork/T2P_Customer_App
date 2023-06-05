package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName
import java.util.*


class Order {

    var id: Int = 0

    @SerializedName("number")
    lateinit var orderNumber: String

    @SerializedName("date_created")
    lateinit var dateCreated: Date

    lateinit var status: String

    var total: Float = 0f

    @SerializedName("billing")
    lateinit var billingAddress: BillingAddress

    @SerializedName("shipping")
    lateinit var shippingAddress: ShippingAddress

    @SerializedName("customer_id")
    var customerId: String? = null

    @SerializedName("line_items")
    var lineItems: MutableList<LineItem> = ArrayList()

    @SerializedName("transaction_id")
    lateinit var transactionid: String

    @SerializedName("set_paid")
    var isPaid: Boolean? = false

    @SerializedName("payment_method")
    var methodId: String? = ""

    @SerializedName("payment_method_title")
    var methodTitle: String? = ""

    @SerializedName("coupon_lines")
    var couponLines: MutableList<CouponDetail> = ArrayList()


    fun addLineItem(lineItem: LineItem) {
        lineItems.add(lineItem)

    }

    @SerializedName("meta")
    lateinit var meta: Metum
}

data class CouponDetail(

    @SerializedName("code")
    var code: String = ""
) {
    @SerializedName("discount")
    var discount: String = ""
}