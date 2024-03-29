package me.taste2plate.app.models.home

import androidx.annotation.Keep

@Keep
data class ProductDeal(
    val __v: Int,
    val _id: String,
    val active: Int,
    val attribute: String,
    val backorders: String,
    val taste: String,
    val batchno: String,
    val brand: String,
    val category: String,
    val cgst: String,
    val city: String,
    val combo_products: Any,
    val commission: String,
    val created_date: String,
    val cuisine: String,
    val deal: Int,
    val deleted: Int,
    val desc: String,
    val discounted_price: String,
    val end_date: String,
    val file: List<FileX>?,
    val height: String,
    val igst: String,
    val length: String,
    val manage_stock: Int,
    val name: String,
    val packaging_charge: String,
    val price: String,
    val selling_price: String,
    val sgst: String,
    val shipping: String,
    val short_desc: String,
    val sku: String,
    val start_date: String,
    val stock_qty: String,
    val sub_category: String,
    val tags: String,
    val tax_status: String,
    val threshold: String,
    val update_date: String,
    val vendor: String,
    val weight: String,
    val width: String
)