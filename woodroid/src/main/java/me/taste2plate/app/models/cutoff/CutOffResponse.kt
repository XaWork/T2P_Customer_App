package me.taste2plate.app.models.cutoff

import androidx.annotation.Keep


@Keep
data class CutOffResponse(
    val message: String,
    val result: Result,
    val status: String
)