package me.taste2plate.app.models

import com.google.gson.annotations.SerializedName


class FeeLine{
    @SerializedName("name")
    var name: String? = null

    @SerializedName("total")
    var total: String = ""
}
