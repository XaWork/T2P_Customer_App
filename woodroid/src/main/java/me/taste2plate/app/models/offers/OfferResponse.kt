package me.taste2plate.app.models.offers

import androidx.annotation.Keep
import me.taste2plate.app.models.home.ProductDeal

@Keep
data class OfferResponse(
    val coupon: List<Coupon>,
    val product_deal: List<ProductDeal>,
    val best_seller: List<ProductDeal>,
    val combo: List<ProductDeal>,
    val status: String
)