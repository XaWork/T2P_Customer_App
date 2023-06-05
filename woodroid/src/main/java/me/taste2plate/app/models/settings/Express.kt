package me.taste2plate.app.models.settings

import androidx.annotation.Keep

@Keep
data class Express(
    val express_popup_bg_color: String,
    val express_popup_desctiption: String,
    val express_popup_subtitle: String,
    val express_popup_title: String,
    val express_popup_title_color: String,
    val express_subtitle_desctiption_color: String,
    val express_subtitle_title_color: String
)