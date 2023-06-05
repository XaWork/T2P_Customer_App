package me.taste2plate.app.repo

import me.taste2plate.app.data.api.CustomerAPI
import me.taste2plate.app.data.api.RegistrationResponse
import me.taste2plate.app.models.*
import me.taste2plate.app.models.filters.CustomerFilter
import me.taste2plate.app.models.report.LoginResponse
import retrofit2.Call

class CustomerRepository(baseUrl: String, consumerKey: String, consumerSecret: String) :
    WooRepository(baseUrl, consumerKey, consumerSecret) {

    private val apiService: CustomerAPI = retrofit.create(CustomerAPI::class.java)

    fun signup(email: String, mobile:String, token:String, referredBy:String?): Call<RegistrationResponse> {
        return apiService.signup(email, mobile, token, "Android", referredBy)
    }


    fun update(id: String, fullName: String, mobile:String): Call<CommonResponse> {
        return apiService.update(id, fullName, mobile)
    }

    fun fetchOtp(loginNumber: String, deviceToken:String): Call<FetchOtpResponse> {
        return apiService.fetchOtp(loginNumber, deviceToken, "Android")
    }

    fun verifyOtp(loginNumber: String,otp: String, deviceToken: String): Call<ValidateOtpResponse> {
        return apiService.verifyOtp(loginNumber,otp, deviceToken)
    }
}
