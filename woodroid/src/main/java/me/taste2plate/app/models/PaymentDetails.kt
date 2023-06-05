package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName


class PaymentDetails {
    @SerializedName("payment_method")
    var methodId: String? = ""
    @SerializedName("payment_method_title")
    var methodTitle: String? = ""
    var paid: Boolean? = null
}
