package me.taste2plate.app.models.order

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Position(
    val coordinates: List<Float>,
    val type: String
):Serializable