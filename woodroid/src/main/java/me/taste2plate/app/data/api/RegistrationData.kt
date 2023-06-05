package me.taste2plate.app.data.api


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegistrationData(
    @SerializedName("about")
    val about: String,
    @SerializedName("active")
    val active: Int,
    @SerializedName("address")
    val address: String?,
    @SerializedName("device_token")
    val deviceToken: String,
    @SerializedName("device_type")
    val deviceType: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_otp")
    val emailOtp: Int,
    @SerializedName("file")
    val `file`: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("otp")
    val otp: Int,
    @SerializedName("profile_image")
    val profileImage: Any?,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("update_date")
    val updateDate: String,
    @SerializedName("user_type")
    val userType: String
)