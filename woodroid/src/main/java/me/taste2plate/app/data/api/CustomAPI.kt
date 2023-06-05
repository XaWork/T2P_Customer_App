package me.taste2plate.app.data.api


import me.taste2plate.app.models.*
import me.taste2plate.app.models.address.check_zip.CheckAvailabilityResponse
import me.taste2plate.app.models.address.check_zip.CutoffResponse
import me.taste2plate.app.models.address.cities.CityListResponse
import me.taste2plate.app.models.address.cityzip.CityZipResponse
import me.taste2plate.app.models.address.states.AllStateResponse
import me.taste2plate.app.models.cutoff.CutOffResponse
import me.taste2plate.app.models.order.updates.OrderUpdateResponse
import me.taste2plate.app.models.ordervalidation.OrderValidation
import me.taste2plate.app.models.zip.ZipListResponse
import retrofit2.Call
import retrofit2.http.*

interface CustomAPI {

    @GET("app/all-cities")
    fun cityList(): Call<CityBrand>

    @GET("app/all-brands")
    fun brandList(): Call<CityBrand>


    @POST("app/bulk-order")
    @FormUrlEncoded
    fun createBulkOrder(@FieldMap bulkOrder: HashMap<String, String>): Call<CommonResponse>


    @GET("app/check-zipcode")
    fun checkAvailability(@Query("zipcode") zipcode: Int, @Query("vendor") id: String): Call<CheckAvailabilityResponse>


    @GET("app/cutofftime-check")
    fun checkCutOfftime(@Query("start_city") startCity: String, @Query("end_city") endCity: String): Call<CutOffResponse>

    @GET("app/delete-address")
    fun deleteAddress(@Query("id") id: String): Call<CommonResponse>

    @GET("app/get-city-zip")
    fun getAllCityZip(): Call<CityZipResponse>

    @GET("app/state-list")
    fun getAllStates(): Call<AllStateResponse>

    @FormUrlEncoded
    @POST("app/add-address")
    fun saveAddress(
        @Field("userid") userId: String,
        @Field("contact_name") name: String,
        @Field("contact_mobile") phone: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("pincode") pincode: String,
        @Field("landmark") postOffice: String,
        @Field("address") addressLine: String,
        @Field("address2") secondary: String,
        @Field("lat") lat: Double,
        @Field("lng") lng: Double,
        @Field("title") type: String
    ): Call<CommonResponse>

    @FormUrlEncoded
    @POST("app/edit-address")
    fun editAddress(
        @Field("id") addressId: String,
        @Field("contact_name") name: String,
        @Field("contact_mobile") phone: String,
        @Field("city") city: String,
        @Field("state") state: String,
        @Field("pincode") pincode: String,
        @Field("landmark") postOffice: String,
        @Field("address") addressLine: String,
        @Field("address2") secondary: String,
        @Field("lat") lat: Double,
        @Field("lng") lng: Double,
        @Field("title") type: String
    ): Call<CommonResponse>

    @FormUrlEncoded
    @POST("app/cancel-order")
    fun cancelOrder(@Field("id") orderId: String): Call<CommonResponse>

    @GET("app/order-updates")
    fun getOrderUpdates(@Query("id") orderId: String): Call<OrderUpdateResponse>

    @POST("app/settings")
    fun fetchAppData(): Call<AppDataResponse>

    @GET("app/zipcode-by-city")
    fun fetchZipList(@Query("city") cityId:String): Call<ZipListResponse>

    @GET("app/city-list-by-state")
    fun fetchCityByState(@Query("state") stateId:String): Call<CityListResponse>

}
