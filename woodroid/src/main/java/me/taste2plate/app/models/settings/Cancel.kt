package me.taste2plate.app.models.settings

import androidx.annotation.Keep

@Keep
data class Cancel(
    val cancel_popup_bg_color: String,
    val cancel_popup_desctiption: String,
    val cancel_popup_subtitle: String,
    val cancel_popup_title: String,
    val cancel_popup_title_color: String,
    val cancel_subtitle_desctiption_color: String,
    val cancel_subtitle_title_color: String
)