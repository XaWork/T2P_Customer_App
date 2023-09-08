package me.taste2plate.app.models

import androidx.annotation.Keep

@Keep
data class LogCreatedResponse(
    val message: String,
    val result: Result,
    val status: String
) {
    @Keep
    data class Result(
        val __v: Int,
        val _id: String,
        val city: String,
        val country: String,
        val createdAt: String,
        val event: String,
        val event_data: String,
        val geo_ip: String,
        val log_category: Any,
        val page_name: String,
        val pincode: String,
        val product_id: String,
        val project: String,
        val source: String,
        val state: String,
        val type: String,
        val updatedAt: String,
        val user: String,
        val user_id: String
    )
}