package me.taste2plate.app.models.home


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class MostOrderedItem(
    val active: Int,
    @SerializedName("added_by")
    val addedBy: String,
    val attribute: String,
    val backorders: String,
    val batchno: String,
    val brand: String,
    val category: String,
    val cgst: String,
    val city: String,
    @SerializedName("combo_products")
    val comboProducts: Any,
    val commission: String,
    val consumable: Int,
    @SerializedName("created_date")
    val createdDate: String,
    val cuisine: String,
    val deal: Int,
    val deleted: Int,
    val desc: String,
    @SerializedName("discounted_price")
    val discountedPrice: String,
    @SerializedName("end_date")
    val endDate: String,
    val express: Boolean,
    val featured: Int,
    val `file`: List<File>,
    val height: String,
    @SerializedName("_id")
    val id: String,
    val igst: String,
    val length: String,
    @SerializedName("manage_stock")
    val manageStock: Int,
    val name: String,
    @SerializedName("packaging_charge")
    val packagingCharge: String,
    val point: Int,
    @SerializedName("point_exp_date")
    val pointExpDate: String,
    val price: Int,
    @SerializedName("selling_price")
    val sellingPrice: Any,
    @SerializedName("seo_description")
    val seoDescription: String,
    @SerializedName("seo_keywords")
    val seoKeywords: String,
    @SerializedName("seo_title")
    val seoTitle: String,
    val sgst: String,
    @SerializedName("short_desc")
    val shortDesc: String,
    val sku: String,
    val slug: String,
    @SerializedName("slug_history")
    val slugHistory: List<String>,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("stock_product")
    val stockProduct: String,
    @SerializedName("stock_qty")
    val stockQty: String,
    @SerializedName("sub_category")
    val subCategory: String,
    val tags: String,
    @SerializedName("tax_status")
    val taxStatus: String,
    val threshold: String,
    val top: Int,
    @SerializedName("update_date")
    val updateDate: String,
    @SerializedName("__v")
    val v: Int,
    val vendor: String,
    val weight: String,
    val width: String
) {
    data class File(
        val acl: String,
        val bucket: String,
        val contentDisposition: Any,
        val contentEncoding: Any,
        val contentType: String,
        val encoding: String,
        val etag: String,
        val fieldname: String,
        val key: String,
        val location: String,
        val metadata: Any,
        val mimetype: String,
        val originalname: String,
        val serverSideEncryption: Any,
        val size: Int,
        val storageClass: String
    )
}