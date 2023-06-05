package me.taste2plate.app.models.address.cityzip

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class CityZipResponse(
    val result: List<CityOption>,
    val status: String
): Serializable