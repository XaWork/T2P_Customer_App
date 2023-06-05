package me.taste2plate.app.models.address.states

import androidx.annotation.Keep

@Keep
data class Result(
    val _id: String,
    val active: Int,
    val city: List<City>,
    val deleted: Int,
    val name: String
)