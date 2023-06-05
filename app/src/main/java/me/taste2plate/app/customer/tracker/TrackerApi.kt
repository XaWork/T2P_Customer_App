package me.taste2plate.app.customer.tracker

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TrackerApi {

    @POST("acquisition")
    fun install(
        @Query("click_id")clickId: String = "1",
        @Query("security_token")securityToken: String = "d3117dafc420ac09ef6b",
    ): Call<TrackerModel>

    @POST("acquisition")
    fun registration(
        @Query("click_id")clickId: String= "2",
        @Query("security_token")securityToken: String = "d3117dafc420ac09ef6b",
        @Query("goal_value")goalValue: String = "Registration",
    ): Call<TrackerModel>

    @POST("acquisition")
    fun orderPlaced(
        @Query("click_id")clickId: String= "3",
        @Query("security_token")securityToken: String = "d3117dafc420ac09ef6b",
        @Query("goal_value")goalValue: String = "Order_Placed",
    ): Call<List<TrackerModel>>


}