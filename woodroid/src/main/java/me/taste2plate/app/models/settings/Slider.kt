package me.taste2plate.app.models.settings

import androidx.annotation.Keep

@Keep
data class Slider(
    val service_popup_bg_color: String,
    val service_popup_desctiption: String,
    val service_popup_subtitle: String,
    val service_popup_title: String,
    val service_popup_title_color: String,
    val service_subtitle_desctiption_color: String,
    val service_subtitle_title_color: String
)