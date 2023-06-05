package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

import java.util.ArrayList


class LineItem {

    lateinit var total: String
    lateinit var price: String
    lateinit var name: String

    @SerializedName("quantity")
    var quantity: Int = 0

    @SerializedName("product_id")
    var productId: String = ""


}
