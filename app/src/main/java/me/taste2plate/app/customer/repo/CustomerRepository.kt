package me.taste2plate.app.customer.repo


import com.google.firebase.auth.FirebaseAuth
import me.taste2plate.app.customer.common.WooLiveData
import me.taste2plate.app.Woocommerce
import me.taste2plate.app.customer.Config
import me.taste2plate.app.data.api.RegistrationResponse
import me.taste2plate.app.models.*
import me.taste2plate.app.models.report.LoginResponse
import me.taste2plate.app.models.filters.CustomerFilter

import javax.inject.Inject

class CustomerRepository @Inject
constructor() {

    @Inject
    lateinit var woocommerce: Woocommerce


    fun signup(email: String, phone:String, token:String, referredBy:String?): WooLiveData<RegistrationResponse> {
        val callBack = WooLiveData<RegistrationResponse>()
        woocommerce.CustomerRepository().signup(email, phone, token, referredBy).enqueue(callBack)
        return callBack
    }


    fun fetchOtp(loginNumber: String, deviceToken:String): WooLiveData<FetchOtpResponse> {
        val callBack = WooLiveData<FetchOtpResponse>()
        woocommerce.CustomerRepository().fetchOtp(loginNumber, deviceToken ).enqueue(callBack)
        return callBack
    }



    fun verifyOtp(loginNumber: String,otp: String, deviceToken: String): WooLiveData<ValidateOtpResponse> {
        val callBack = WooLiveData<ValidateOtpResponse>()
        woocommerce.CustomerRepository().verifyOtp(loginNumber,otp, deviceToken).enqueue(callBack)
        return callBack
    }


    fun update(id: String, fullName: String, mobile:String): WooLiveData<CommonResponse> {
        val callBack = WooLiveData<CommonResponse>()
        woocommerce.CustomerRepository().update(id, fullName, mobile).enqueue(callBack)
        return callBack
    }
}
