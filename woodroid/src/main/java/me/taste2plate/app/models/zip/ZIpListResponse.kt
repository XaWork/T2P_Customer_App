package me.taste2plate.app.models.zip


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.address.cityzip.Zip

@Keep
data class ZipListResponse(
    @SerializedName("result")
    val result: List<Zip>,
    @SerializedName("status")
    val status: String
)