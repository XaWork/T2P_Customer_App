package me.taste2plate.app.data.api

import me.taste2plate.app.models.order.MyOrderResponse
import retrofit2.Call
import retrofit2.http.*

interface OrderAPI {
    
    @GET("app/my-orders")
    fun history(@Query("id") userId:String): Call<MyOrderResponse>

}