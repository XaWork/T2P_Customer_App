package me.taste2plate.app.models.home

import androidx.annotation.Keep
import me.taste2plate.app.models.address.cityzip.CityOption

@Keep
data class Slider(
    val __v: Int,
    val _id: String,
    val active: Int,
    val city: CityOption,
    val created_date: String,
    val deleted: Int,
    val file: String,
    val name: String,
    val update_date: String
)