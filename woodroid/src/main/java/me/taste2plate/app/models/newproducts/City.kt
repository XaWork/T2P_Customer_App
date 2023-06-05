package me.taste2plate.app.models.newproducts

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class City(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String
)