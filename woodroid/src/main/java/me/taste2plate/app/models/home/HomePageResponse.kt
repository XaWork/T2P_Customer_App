package me.taste2plate.app.models.home

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.Category
import me.taste2plate.app.models.CityBrand
import me.taste2plate.app.models.address.cityzip.CityOption

@Keep
data class HomePageResponse(
    val best_seller: ArrayList<ProductDeal>,
    val category: List<Category.Result>,
    val message: String,
    val product_deal: ArrayList<ProductDeal>,
    val combo: ArrayList<ProductDeal>,
    val featured: ArrayList<ProductDeal>,
    val slider: ArrayList<Slider>,
    val cuisine: ArrayList<CityBrand.Result>,
    val city: ArrayList<CityOption>,
    @SerializedName("top_brands")
    val topBrands: ArrayList<TopBrands>,
    @SerializedName("hidden_gems")
    val hiddenGems: ArrayList<HiddenGems>,
    @SerializedName("most_orderd_item")
    val mostOrderedItem: ArrayList<ProductDeal>,
    @SerializedName("top_most_ordered_products")
    val top_most_ordered_products: ArrayList<TopMostOrderedProducts>,
    val status: String
)