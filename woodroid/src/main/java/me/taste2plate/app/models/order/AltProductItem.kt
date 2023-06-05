package me.taste2plate.app.models.order


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class AltProductItem(
    @SerializedName("active")
    val active: Int,
    @SerializedName("attribute")
    val attribute: String,
    @SerializedName("backorders")
    val backorders: String,
    @SerializedName("batchno")
    val batchno: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("cgst")
    val cgst: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("combo_products")
    val comboProducts: Any,
    @SerializedName("commission")
    val commission: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("cuisine")
    val cuisine: String,
    @SerializedName("deal")
    val deal: Int,
    @SerializedName("deleted")
    val deleted: Int,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("discounted_price")
    val discountedPrice: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("express")
    val express: Boolean,
    @SerializedName("featured")
    val featured: Int,
    @SerializedName("file")
    val file: List<File>,
    @SerializedName("height")
    val height: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("igst")
    val igst: String,
    @SerializedName("length")
    val length: String,
    @SerializedName("manage_stock")
    val manageStock: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("packaging_charge")
    val packagingCharge: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("selling_price")
    val sellingPrice: Int,
    @SerializedName("sgst")
    val sgst: String,
    @SerializedName("short_desc")
    val shortDesc: String,
    @SerializedName("sku")
    val sku: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("slug_history")
    val slugHistory: List<String>,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("stock_qty")
    val stockQty: String,
    @SerializedName("sub_category")
    val subCategory: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("tax_status")
    val taxStatus: String,
    @SerializedName("threshold")
    val threshold: String,
    @SerializedName("update_date")
    val updateDate: String,
    @SerializedName("__v")
    val v: Int,
    @SerializedName("vendor")
    val vendor: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("width")
    val width: String
):Serializable