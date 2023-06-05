package me.taste2plate.app.data.api


import com.google.gson.annotations.SerializedName

data class CargoPosition(
    @SerializedName("coordinates")
    val coordinates: List<Int>,
    @SerializedName("type")
    val type: String
)