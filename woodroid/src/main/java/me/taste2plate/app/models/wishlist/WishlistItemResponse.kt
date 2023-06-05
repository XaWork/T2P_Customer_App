package me.taste2plate.app.models.wishlist


import com.google.gson.annotations.SerializedName

data class WishlistItemResponse(
    val result: List<Result>,
    val status: String // success
) {
    data class Result(
        @SerializedName("created_date")
        val createdDate: String, // 2022-06-17T06:25:32.689Z
        @SerializedName("_id")
        val id: String, // 62adc3b767b5ea75b0ae94f8
        val product: Product,
        @SerializedName("update_date")
        val updateDate: String, // 2022-06-17T06:25:32.689Z
        val user: String, // 60f9ab7cbdc62d0009886ce4
        @SerializedName("__v")
        val v: Int // 0
    ) {
        data class Product(
            val active: Int, // 1
            @SerializedName("added_by")
            val addedBy: String?, // 60a1ccf6db7c43000ae24579
            val attribute: String,
            val backorders: String,
            val batchno: String,
            val brand: Brand,
            val category: Category,
            val cgst: String, // 2.5
            val city: City,
            @SerializedName("combo_products")
            val comboProducts: Any?, // null
            val commission: String,
            val consumable: Int, // 0
            @SerializedName("created_date")
            val createdDate: String, // 2021-03-30T06:59:34.247Z
            val cuisine: Cuisine,
            val deal: Int?, // 1
            val deleted: Int, // 0
            val desc: String, // <p style="margin: 3.75pt 0cm 27.75pt; background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;">Butter chick <span style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;">&nbsp;creamy&nbsp;</span><span style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;">chicken</span><span style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;">&nbsp;dish where marinated&nbsp;</span><span style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;">chicken</span><span style="background-image: initial; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial;">&nbsp;is simmered in a spicy masala and cream.&nbsp;</span><o:p></o:p>Butter Chicken or murgh makhani is a curry of chicken in a spiced tomato, butter and cream sauce. It originated in India as a curry.</p>
            @SerializedName("discounted_price")
            val discountedPrice: String,
            @SerializedName("end_date")
            val endDate: String, // 2022-06-30T00:00:00.000Z
            val express: Boolean, // true
            val featured: Int, // 0
            val `file`: List<File>,
            val height: String,
            @SerializedName("_id")
            val id: String, // 6062cc5611cf3a000798c397
            val igst: String, // 5
            val length: String,
            @SerializedName("manage_stock")
            val manageStock: Int, // 0
            val name: String, // Butter Chicken - Shadab (Hyderabad)
            @SerializedName("packaging_charge")
            val packagingCharge: String, // 30
            val point: Int, // 0
            @SerializedName("point_exp_date")
            val pointExpDate: String, // 2022-11-18T00:00:00.000Z
            val price: Int, // 340
            @SerializedName("selling_price")
            val sellingPrice: Int?, // 315
            @SerializedName("seo_description")
            val seoDescription: String,
            @SerializedName("seo_keywords")
            val seoKeywords: String,
            @SerializedName("seo_title")
            val seoTitle: String,
            val sgst: String, // 2.5
            @SerializedName("short_desc")
            val shortDesc: String, // Butter chick  creamy chicken dish where marinated chicken is simmered in a spicy masala and cream.
            val sku: String,
            val slug: String, // butter-chicken-shadab-hyderabad
            @SerializedName("slug_history")
            val slugHistory: List<String>,
            @SerializedName("start_date")
            val startDate: String, // 2021-03-30T00:00:00.000Z
            @SerializedName("stock_product")
            val stockProduct: String, // 6049f3db426ac9000878ddf6
            @SerializedName("stock_qty")
            val stockQty: String, // 500
            @SerializedName("sub_category")
            val subCategory: SubCategory,
            val tags: String,
            @SerializedName("tax_status")
            val taxStatus: String,
            val threshold: String,
            @SerializedName("update_date")
            val updateDate: String, // 2021-03-30T06:59:34.247Z
            @SerializedName("__v")
            val v: Int, // 0
            val vendor: Vendor,
            val weight: String, // 0.6
            val width: String
        ) {
            data class Brand(
                @SerializedName("_id")
                val id: String, // 6051f37c8f54da00086b20b3
                val name: String // Shadab - Hyderabad
            )

            data class Category(
                @SerializedName("_id")
                val id: String, // 6050bb1256cb9d00088521bc
                val name: String // COOKED FOOD
            )

            data class City(
                @SerializedName("_id")
                val id: String, // 6049f399426ac9000878ddf5
                val name: String // HYDERABAD(Both)
            )

            data class Cuisine(
                @SerializedName("_id")
                val id: String, // 603f70aff0fabb000820850a
                val name: String // Punjabi
            )

            data class File(
                val acl: String, // public-read
                val bucket: String, // t2p
                val contentDisposition: Any?, // null
                val contentEncoding: Any?, // null
                val contentType: String, // application/octet-stream
                val encoding: String, // 7bit
                val etag: String, // "5b7041ca8f1d64628346ebb1b07f79c1"
                val fieldname: String, // upload
                val key: String, // 16170875734124s1p8.jpeg
                val location: String, // https://t2p.fra1.digitaloceanspaces.com/16170875734124s1p8.jpeg
                val metadata: Any?, // null
                val mimetype: String, // image/jpeg
                val originalname: String, // 1.jpg
                val serverSideEncryption: Any?, // null
                val size: Int, // 99877
                val storageClass: String, // STANDARD
                val versionId: Any? // null
            )

            data class SubCategory(
                @SerializedName("_id")
                val id: String, // 60546b27595327000817509f
                val name: String // Cooked Chicken Products
            )

            data class Vendor(
                @SerializedName("full_name")
                val fullName: String, // Shadab - Hyderabad
                @SerializedName("_id")
                val id: String // 6058647d4103cc000817807a
            )
        }
    }
}