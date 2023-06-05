package me.taste2plate.app.models.address.cityzip

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class CityOption(
    val __v: Int,
    val _id: String,
    val active: Int,
    val created_date: String,
    val deleted: Int,
    val description: String,
    val description_after_content: String,
    val file: String,
    val name: String,
    val ps: String,
    val update_date: String,
    val zips: List<Zip>
): Serializable