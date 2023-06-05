package me.taste2plate.app.data.api


import me.taste2plate.app.models.CommonResponse
import me.taste2plate.app.models.FetchOtpResponse
import me.taste2plate.app.models.ValidateOtpResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CustomerAPI {


    @FormUrlEncoded
    @POST("app/registration")
    fun signup(
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("device_token") token: String,
        @Field("device_type") type: String,
        @Field("reffer") referredBy: String?
    ): Call<RegistrationResponse>

    @FormUrlEncoded
    @POST("app/edit-profile")
    fun update(
        @Field("id") id: String,
        @Field("full_name") fullName: String,
        @Field("mobile") mobile: String
    ): Call<CommonResponse>


    @FormUrlEncoded
    @POST("app/login")
    fun fetchOtp(
        @Field("mobile") mobile: String,
        @Field("device_token") token: String,
        @Field("device_type") device: String
    ): Call<FetchOtpResponse>


    @FormUrlEncoded
    @POST("app/verify-otp")
    fun verifyOtp(
        @Field("mobile") phone: String,
        @Field("otp") otp: String,
        @Field("device_token") deviceToken: String
    ): Call<ValidateOtpResponse>

}