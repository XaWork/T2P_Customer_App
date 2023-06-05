package me.taste2plate.app.models

data class DashboardItemResponse(
	val code: Int? = null,
	val data: List<DataItem>,
	val message: String? = null
)

data class DataItem(
	val main_image_1: String? = null,
	val id: Int? = null,
	val title: String? = null,
	val main_image_2: String? = null
)

