package me.taste2plate.app.models.address.states

import androidx.annotation.Keep

@Keep
data class AllStateResponse(
    val result: List<Result>,
    val status: String
)