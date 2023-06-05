package me.taste2plate.app.models.address.states

import androidx.annotation.Keep


@Keep
data class City(
    val __v: Int,
    val _id: String,
    val active: Int,
    val cod: Boolean,
    val created_date: String,
    val deleted: Int,
    val description: String,
    val description_after_content: String,
    val `file`: String,
    val name: String,
    val ps: String,
    val state: String,
    val update_date: String
)