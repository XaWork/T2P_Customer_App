package me.taste2plate.app.models.wishlist


import com.google.gson.annotations.SerializedName

data class DeleteWishlistItemResponse(
    val message: String, // Product deleted from wish list successfully
    val result: Result,
    val status: String // success
) {
    data class Result(
        @SerializedName("created_date")
        val createdDate: String, // 2022-06-17T06:25:32.689Z
        @SerializedName("_id")
        val id: String, // 62adc4d667b5ea75b0ae9af8
        val product: String, // 6062cc5611cf3a000798c397
        @SerializedName("update_date")
        val updateDate: String, // 2022-06-17T06:25:32.689Z
        val user: String, // 60f9ab7cbdc62d0009886ce4
        @SerializedName("__v")
        val v: Int // 0
    )
}