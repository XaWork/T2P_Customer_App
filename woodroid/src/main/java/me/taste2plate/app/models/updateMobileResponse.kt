package me.taste2plate.app.models

data class UpdateMobileResponse(
    val code: Int? = null,
    val data: UpdateResponseData? = null,
    val message: String? = null
)

data class UpdateResponseData(
    val user_id: String? = null,
    val mobile_number: String? = null
)

