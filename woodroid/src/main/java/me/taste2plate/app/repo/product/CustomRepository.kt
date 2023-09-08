package me.taste2plate.app.repo.product

import me.taste2plate.app.data.api.ApiService
import me.taste2plate.app.data.api.CustomAPI
import me.taste2plate.app.data.api.LogRequest
import me.taste2plate.app.models.*
import me.taste2plate.app.models.address.check_zip.CheckAvailabilityResponse
import me.taste2plate.app.models.address.cities.CityListResponse
import me.taste2plate.app.models.address.cityzip.CityZipResponse
import me.taste2plate.app.models.address.states.AllStateResponse
import me.taste2plate.app.models.custom.BulkOrder
import me.taste2plate.app.models.custom.DirectFromHome
import me.taste2plate.app.models.cutoff.CutOffResponse
import me.taste2plate.app.models.order.updates.OrderUpdateResponse
import me.taste2plate.app.models.ordervalidation.OrderValidation
import me.taste2plate.app.models.zip.ZipListResponse
import me.taste2plate.app.repo.WooRepository
import org.apache.http.util.TextUtils
import retrofit2.Call

class CustomRepository(baseUrl: String, consumerKey: String, consumerSecret: String) :
    WooRepository(baseUrl, consumerKey, consumerSecret) {

    private var apiService: CustomAPI = retrofitWithAuth.create(CustomAPI::class.java)
    private var analyticsAPI: ApiService = retrofitAnalytics.create(ApiService::class.java)
    private var trackerApi: ApiService = retrofitTracker.create(ApiService::class.java)

    fun reInite() {
        getUpdatedAuth()
    }

    fun addLog(request: LogRequest): Call<LogCreatedResponse> {
        return analyticsAPI.addLog1(request)
    }

    fun install(
        clickId: String,
        security_token: String,
        tracker_record: String,
        gaid: String,
        sub4: String,
    ): Call<TrackerResponse> {
        return trackerApi.install(tracker_record, clickId, security_token, gaid, sub4)
    }

    fun purchased(
        tracker_record: String,
        clickId: String,
        security_token: String,
        gaid: String,
        sub4: String,
        goal_name : String,
        sale_amount : String,
    ): Call<TrackerResponse> {
        return trackerApi.purchased(tracker_record,clickId, security_token, gaid, sub4, goal_name, sale_amount)
    }

    fun getCity(): Call<CityBrand> {
        return apiService.cityList()
    }

    fun getBrands(): Call<CityBrand> {
        return apiService.brandList()
    }


    fun createBulkOrder(bulkOrder: BulkOrder): Call<CommonResponse> {

        return apiService.createBulkOrder(bulkOrder.getKeyValuePair())
    }


    fun checkAvailability(zipCode: Int, vendorId: String): Call<CheckAvailabilityResponse> {
        return apiService.checkAvailability(zipCode, vendorId)
    }

    fun checkCutOfftime(startCity: String, endCity: String): Call<CutOffResponse> {
        return apiService.checkCutOfftime(startCity, endCity)
    }


    fun deleteAddress(
        userId: String,
        addressId: String
    ): Call<CommonResponse> {

        return apiService.deleteAddress(addressId)
    }

    fun getAllCityZip(): Call<CityZipResponse> {
        return apiService.getAllCityZip()
    }

    fun getAllStates(): Call<AllStateResponse> {
        return apiService.getAllStates()
    }

    fun saveAddress(
        userId: String,
        name: String,
        phone: String,
        city: String,
        state: String,
        pincode: String,
        postOffice: String,
        addressLine: String,
        secondary: String,
        lat: Double,
        lng: Double,
        type: String
    ): Call<CommonResponse> {
        return apiService.saveAddress(
            userId,
            name,
            phone,
            city,
            state,
            pincode,
            postOffice,
            addressLine,
            secondary,
            lat,
            lng,
            type
        )
    }

    fun editAddress(
        addressId: String,
        name: String,
        phone: String,
        city: String,
        state: String,
        pincode: String,
        postOffice: String,
        addressLine: String,
        secondary: String,
        lat: Double,
        lng: Double,
        type: String
    ): Call<CommonResponse> {
        return apiService.editAddress(
            addressId,
            name,
            phone,
            city,
            state,
            pincode,
            postOffice,
            addressLine,
            secondary,
            lat,
            lng,
            type
        )
    }


    fun cancelOrder(orderId: String): Call<CommonResponse> {
        return apiService.cancelOrder(orderId)
    }

    fun getOrderUpdates(orderId: String): Call<OrderUpdateResponse> {
        return apiService.getOrderUpdates(orderId)
    }

    fun fetchAppData(): Call<AppDataResponse> {
        return apiService.fetchAppData()
    }


    fun fetchZipList(cityId: String): Call<ZipListResponse> {
        return apiService.fetchZipList(cityId)
    }

    fun fetchCityByState(stateId: String): Call<CityListResponse> {
        return apiService.fetchCityByState(stateId)
    }

}
