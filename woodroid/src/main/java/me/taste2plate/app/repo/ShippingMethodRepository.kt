package me.taste2plate.app.repo

import com.google.gson.JsonElement
import me.taste2plate.app.data.api.ShippingMethodAPI
import me.taste2plate.app.models.ShippingMethod
import me.taste2plate.app.models.ShippingMethods
import retrofit2.Call

class ShippingMethodRepository(baseUrl: String, consumerKey: String, consumerSecret: String) :
    WooRepository(baseUrl, consumerKey, consumerSecret) {

    private val apiService: ShippingMethodAPI

    init {
        apiService = retrofit.create(ShippingMethodAPI::class.java)
    }

    fun view(id: String): Call<ShippingMethod> {
        return apiService.view(id)
    }

    fun getShippingMethod(id: Int): Call<JsonElement> {
        return apiService.getShippingMethod(id)
    }

    fun shippingMethods(): Call<List<ShippingMethod>> {
        return apiService.list()
    }


}
