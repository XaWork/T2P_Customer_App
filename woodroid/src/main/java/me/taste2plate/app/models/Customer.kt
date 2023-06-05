package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable


class Customer : Serializable {

    @SerializedName("id")
    var id: String = ""

    @SerializedName("created_at")
    lateinit var createdAt: String

    lateinit var email: String

    @SerializedName("first_name")
    lateinit var firstName: String

    @SerializedName("last_name")
    lateinit var lastName: String

    @SerializedName("user_billing_phone")
    var mobileNumber: String?=""

    lateinit var username: String
    lateinit var password: String
    lateinit var role: String

    @SerializedName("last_order_id")
    lateinit var lastOrderId: String

    @SerializedName("last_order_date")
    lateinit var lastOrderDate: String

    @SerializedName("orders_count")
    var ordersCount: Int = 0

    @SerializedName("total_spent")
    lateinit var totalSpent: String

    @SerializedName("avatar_url")
    lateinit var avatarUrl: String

    @SerializedName("billing")
    var billingAddress: BillingAddress? = null

    @SerializedName("shipping")
    lateinit var shippingAddress: ShippingAddress

}
