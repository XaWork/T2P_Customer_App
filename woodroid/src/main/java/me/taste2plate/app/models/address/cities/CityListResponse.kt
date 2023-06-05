package me.taste2plate.app.models.address.cities


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CityListResponse(
    @SerializedName("result")
    val result: List<CityListResult>,
    @SerializedName("status")
    val status: String
)