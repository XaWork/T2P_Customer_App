package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.filters.ListFilter
import java.util.*

class CityBrandFilter () {

    internal var filters: MutableMap<String, String> = HashMap()

    @SerializedName("product_city")
    var productCity: String?= "all"
        set(productCity) {
            field = productCity

            if (productCity != null) {
                addFilter("product_city", productCity)
            }
        }

    @SerializedName("s")
    var searchItem: String?= "all"
        set(searchItem) {
            field = searchItem

            if (searchItem != null) {
                addFilter("s", searchItem)
            }
        }


    @SerializedName("post_type")
    var post_type:String?="product"
        set(post_type) {
            field = post_type

            if (searchItem != null) {
                addFilter("post_type", "product")
            }
        }


    @SerializedName("brand")
    var brand:String?="all"
        set(brand) {
            field = brand

            if (brand != null) {
                addFilter("brand", brand)
            }

        }

    @SerializedName("product_community")
    var productCommunity:String?="all"
        set(productCommunity) {
            field = productCommunity

            if (productCommunity != null) {
                addFilter("product_community", productCommunity)
            }

        }


    fun addFilter(filter: String, value: Any) {
        if (value.javaClass.isArray) {
            if (value is IntArray) {
                var values = ""
                for (item in value) {
                    values += "$item,"
                }

                filters[filter] = "" + values.substring(0, values.length - 1)

            } else {
                val list = Arrays.asList(value)
                for (item in list) {
                    filters[filter] = item.toString().replace("\"","")
                }
            }
        } else {
            filters[filter] = value.toString().replace("\"","")
        }
    }

    fun getFilters(): Map<String, String> {
        return filters
    }

}
