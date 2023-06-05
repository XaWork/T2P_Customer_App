package me.taste2plate.app.models

import androidx.annotation.Keep
import me.taste2plate.app.data.api.RegistrationData

@Keep
data class ValidateOtpResponse(
    val status: String,
    val data: RegistrationData? = null,
    val message: String? = null)

//
//data class LoginResponse(
//    val user_id:String?="",
//    val token:String?="",
//    val details: String? = null,
//    val status: String? = null
//)

