package me.taste2plate.app.models

data class MakakhanaDetailsResponse(
    val code: Int? = null,
    val data: Details,
    val message: String? = null
)

data class Details(
    val description_1: String? = null,
    val description_2: String? = null,
    val heading_1: String? = null,
    val description_3: String? = null,
    val heading_2: String? = null,
    val description_4: String? = null,
    val heading_3: String? = null,
    val description_5: String? = null,
    val heading_4: String? = null,
    val icon_2: String? = null,
    val heading_5: String? = null,
    val icon_3: String? = null,
    val icon_4: String? = null,
    val icon_5: String? = null,
    val icon_1: String? = null
)

