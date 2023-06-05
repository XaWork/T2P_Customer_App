package me.taste2plate.app.models.settings

import androidx.annotation.Keep

@Keep
data class Popup(
    val popup_bg_color: String,
    val popup_subtitle: String,
    val popup_title: String,
    val popup_title_color: String,
    val subtitle_desctiption_color: String,
    val subtitle_title_color: String
)