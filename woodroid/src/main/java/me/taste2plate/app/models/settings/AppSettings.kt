package me.taste2plate.app.models.settings

import androidx.annotation.Keep
import me.taste2plate.app.models.InfoPopup

@Keep
data class AppSettings(
    val cancel: Cancel,
    val cod: Cod,
    val express: Express,
    val header_bg_color: String,
    val order: Order,
    val popup: Popup,
    val slider: Slider,
    val info_popup : InfoPopup
)