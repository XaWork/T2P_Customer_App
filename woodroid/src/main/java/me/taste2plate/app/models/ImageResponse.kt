package me.taste2plate.app.models

data class ImageResponse(
	val code: Int? = null,
	val data: ImageData? = null,
	val message: String? = null
)

data class ImageData(
	val main_img: String? = null,
	val status: String? = null
)

