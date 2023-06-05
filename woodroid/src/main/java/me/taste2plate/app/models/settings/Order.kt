package me.taste2plate.app.models.settings

import androidx.annotation.Keep

@Keep
data class Order(
    val order_track_popup_bg_color: String,
    val order_track_popup_desctiption: String,
    val order_track_popup_subtitle: String,
    val order_track_popup_title: String,
    val order_track_popup_title_color: String,
    val order_track_subtitle_desctiption_color: String,
    val order_track_subtitle_title_color: String
)