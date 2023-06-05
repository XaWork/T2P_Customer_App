package me.taste2plate.app.data.api

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RegistrationResponse (
    val status:String?,
    val message: String
)