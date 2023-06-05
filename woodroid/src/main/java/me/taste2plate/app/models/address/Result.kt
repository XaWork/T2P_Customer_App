package me.taste2plate.app.models.address

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class Address(
    val address: String, // Abc new, abc
    val address2: String, // Abc
    val city: City,
    @SerializedName("contact_mobile")
    val contact_mobile: String, // 8010265036
    @SerializedName("contact_name")
    val contact_name: String, // do not ship
    @SerializedName("created_date")
    val createdDate: String, // 2022-03-12T08:41:43.558Z
    val deleted: Int, // 0
    @SerializedName("_id")
    var _id: String, // 622c5cc7567ad55711d9ad7f
    val landmark: String?, // land mark 2
    val pincode: String, // 110022
    val position: Position,
    @SerializedName("post_office")
    val postOffice: String?, // N/A
    val state: State,
    val title: String, // home
    @SerializedName("update_date")
    val updateDate: String, // 2022-03-09T16:46:59.481Z
    val user: String, // 6083ea4be2c8890008fbb05a
    @SerializedName("__v")
    val v: Int // 0
):Serializable

@Keep
data class State(val _id:String, val name:String):Serializable

@Keep
data class City(
    @SerializedName("_id")
    val _id: String, // 6040eb2b4a4b6c0008fe1b01
    val name: String // DELHI (NCR)- Delivery
):Serializable


@Keep
data class Position(
    val coordinates: List<Double>,
    val type: String // Point
):Serializable

fun Address.toSummary() = buildString {
    appendln(address)
    appendln(address2)
    appendln(city?.name.orEmpty())
    appendln(state.name)
    append("Pin: $pincode")
}