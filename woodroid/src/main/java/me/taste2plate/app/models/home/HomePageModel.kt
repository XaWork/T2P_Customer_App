package me.taste2plate.app.models.home


import com.google.gson.annotations.SerializedName

data class HomePageModel(
    @SerializedName("best_seller")
    val bestSeller: List<Any>,
    val category: List<Category>,
    val city: List<City>,
    val combo: List<Any>,
    val cuisine: List<Cuisine>,
    val featured: List<Featured>,
    @SerializedName("hidden_gems")
    val hiddenGems: List<HiddenGem>,
    val message: String,
    @SerializedName("most_orderd_item")
    val mostOrderdItem: List<MostOrderdItem>,
    @SerializedName("product_deal")
    val productDeal: List<ProductDeal>,
    @SerializedName("service_city")
    val serviceCity: List<ServiceCity>,
    val slider: List<Slider>,
    val special: List<Special>,
    val status: String,
    @SerializedName("top_brands")
    val topBrands: List<TopBrand>
) {
    data class Category(
        val active: Int,
        @SerializedName("created_date")
        val createdDate: String,
        val deleted: Int,
        val description: String,
        @SerializedName("description_after_content")
        val descriptionAfterContent: String,
        val `file`: String,
        @SerializedName("_id")
        val id: String,
        val name: String,
        val parent: Any,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class City(
        val active: Int,
        val cod: Boolean,
        @SerializedName("created_date")
        val createdDate: String,
        val deleted: Int,
        val description: String,
        @SerializedName("description_after_content")
        val descriptionAfterContent: String,
        val `file`: String,
        val file2: String,
        val footer: Int,
        @SerializedName("footer_content")
        val footerContent: String,
        val heading: String,
        @SerializedName("_id")
        val id: String,
        val name: String,
        val ps: String,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        val state: String,
        @SerializedName("sub_heading")
        val subHeading: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class Cuisine(
        val active: Int,
        @SerializedName("created_date")
        val createdDate: String,
        val deleted: Int,
        val description: String,
        @SerializedName("description_after_content")
        val descriptionAfterContent: String,
        val `file`: String,
        @SerializedName("_id")
        val id: String,
        val name: String,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class Featured(
        val active: Int,
        @SerializedName("added_by")
        val addedBy: Any,
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
        val sellingPrice: Int,
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
            val storageClass: String,
            val versionId: Any
        )
    }

    data class HiddenGem(
        val active: Int,
        @SerializedName("created_date")
        val createdDate: String,
        val deleted: Int,
        val desc: String,
        val `file`: String,
        val gem: Int,
        @SerializedName("_id")
        val id: String,
        val name: String,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        @SerializedName("short_desc")
        val shortDesc: String,
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        val top: Int,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class MostOrderdItem(
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
        val sellingPrice: Int,
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

    data class ProductDeal(
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
        val sellingPrice: Int,
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
            val storageClass: String,
            val versionId: Any
        )
    }

    data class ServiceCity(
        val active: Int,
        val cod: Boolean,
        @SerializedName("created_date")
        val createdDate: String,
        val deleted: Int,
        val description: String,
        @SerializedName("description_after_content")
        val descriptionAfterContent: String,
        val `file`: String,
        val file2: String,
        val footer: Int,
        @SerializedName("footer_content")
        val footerContent: String,
        val heading: String,
        @SerializedName("_id")
        val id: String,
        val name: String,
        val ps: String,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        val state: String,
        @SerializedName("sub_heading")
        val subHeading: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class Slider(
        val active: Int,
        val city: City,
        @SerializedName("created_date")
        val createdDate: String,
        val deleted: Int,
        val `file`: String,
        @SerializedName("_id")
        val id: String,
        val name: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    ) {
        data class City(
            @SerializedName("_id")
            val id: String,
            val name: String
        )
    }

    data class Special(
        val active: Int,
        val amount: String,
        val brand: List<String>,
        val category: List<String>,
        val coupon: String,
        @SerializedName("created_date")
        val createdDate: String,
        val customer: List<String>,
        val deleted: Int,
        val desc: String,
        val exclusive: String,
        @SerializedName("exp_date")
        val expDate: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("max_usage_limit")
        val maxUsageLimit: Int,
        @SerializedName("max_usage_limit_user")
        val maxUsageLimitUser: Int,
        @SerializedName("maximum_amount")
        val maximumAmount: String,
        @SerializedName("minimum_amount")
        val minimumAmount: String,
        val product: List<String>,
        @SerializedName("start_date")
        val startDate: String,
        val type: String,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )

    data class TopBrand(
        val active: Int,
        @SerializedName("created_date")
        val createdDate: String,
        val deleted: Int,
        val desc: String,
        val `file`: String,
        val gem: Int,
        @SerializedName("_id")
        val id: String,
        val name: String,
        @SerializedName("seo_description")
        val seoDescription: String,
        @SerializedName("seo_keywords")
        val seoKeywords: String,
        @SerializedName("seo_title")
        val seoTitle: String,
        @SerializedName("short_desc")
        val shortDesc: String,
        val slug: String,
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        val top: Int,
        @SerializedName("update_date")
        val updateDate: String,
        @SerializedName("__v")
        val v: Int
    )
}