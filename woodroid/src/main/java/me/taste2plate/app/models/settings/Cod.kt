package me.taste2plate.app.models.settings

import androidx.annotation.Keep

@Keep
data class Cod(
    val cod_popup_bg_color: String,
    val cod_popup_desctiption: String,
    val cod_popup_subtitle: String,
    val cod_popup_title: String,
    val cod_popup_title_color: String,
    val cod_subtitle_desctiption_color: String,
    val cod_subtitle_title_color: String
)