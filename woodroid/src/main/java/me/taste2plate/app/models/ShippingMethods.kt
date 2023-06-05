package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class ShippingMethods(

    @field:SerializedName("settings")
    val settings: Settings? = null,

    @field:SerializedName("instance_id")
    val instanceId: Int? = null,

    @field:SerializedName("method_id")
    val methodId: String? = null,

    @field:SerializedName("method_description")
    val methodDescription: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("enabled")
    val enabled: Boolean? = null,

    @field:SerializedName("method_title")
    val methodTitle: String? = null,

    @field:SerializedName("order")
    val order: Int? = null
)