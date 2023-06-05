package me.taste2plate.app.models.order

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.newproducts.NewProduct
import java.io.Serializable

@Keep
data class OrderProductItem(
    @SerializedName("brand")
    val brand: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("cuisine")
    val cuisine: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("sub_category")
    val subCategory: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("vendor")
    val vendor: String,
    @SerializedName("product")
    val product: AltProductItem,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("price")
    val price: Float
):Serializable

