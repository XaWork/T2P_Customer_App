package me.taste2plate.app.models.address.cityzip

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Zip(
    val __v: Int,
    val _id: String,
    val active: Int,
    val additional_cost: Any,
    val city: String,
    val created_date: String,
    val deleted: Int,
    val name: String,
    val update_date: String
):Serializable