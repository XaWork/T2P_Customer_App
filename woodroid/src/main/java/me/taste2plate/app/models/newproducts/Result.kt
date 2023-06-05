package me.taste2plate.app.models.newproducts

import androidx.annotation.Keep

@Keep
data class NewProduct(
    val __v: Int,
    val _id: String,
    val active: Int,
    val attribute: String,
    val backorders: String,
    val batchno: String,
    val brand: Brand,
    val category: Category,
    val cgst: String,
    val combo_products: List<String>,
    val commission: String,
    val created_date: String,
    val cuisine: Cuisine,
    val deal: Int,
    val deleted: Int,
    val desc: String,
    val end_date: String,
    val file: List<File>?,
    val height: String,
    val igst: String,
    val length: String,
    val manage_stock: Int,
    val name: String,
    val packaging_charge: String,
    val price: String,
    val selling_price: String,
    val sgst: String,
    val short_desc: String,
    val sku: String,
    val city:City,
    val start_date: String,
    val stock_qty: String,
    val sub_category: SubCategory,
    val tags: String,
    val tax_status: String,
    val threshold: String,
    val update_date: String,
    val vendor: Vendor,
    val weight: String,
    val width: String
)
@Keep
data class Shipping(val price:String, val _id:String)


@Keep
open class ShowError { }