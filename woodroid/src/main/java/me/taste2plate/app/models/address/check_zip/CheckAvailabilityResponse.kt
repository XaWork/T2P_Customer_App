package me.taste2plate.app.models.address.check_zip

import androidx.annotation.Keep

@Keep
data class CheckAvailabilityResponse(
    val additional_cost: String,
    val cutoff_response: CutoffResponse,
    val express: Boolean,
    val message: String,
    val status: String,
    val cod: Boolean
)