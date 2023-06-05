package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName

data class Cost(

    @field:SerializedName("default")
    val jsonMemberDefault: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("tip")
    val tip: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("label")
    val label: String? = null,

    @field:SerializedName("placeholder")
    val placeholder: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("value")
    val value: String? = null,

    @field:SerializedName("comparedId")
    val comparedId: String? = null?.get {
        id?.replace("class_cost_","")
    }

)