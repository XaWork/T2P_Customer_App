package me.taste2plate.app.data.api


import com.google.gson.JsonElement
import me.taste2plate.app.models.ShippingMethod
import me.taste2plate.app.models.ShippingMethods
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ShippingMethodAPI {

    @GET("shipping_methods/{id}")
    fun view(@Path("id") id: String): Call<ShippingMethod>

    @GET("shipping/zones/{id}/methods")
    fun getShippingMethod(@Path("id") id: Int): Call<JsonElement>

    @GET("shipping_methods")
    fun list(): Call<List<ShippingMethod>>

}