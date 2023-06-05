package me.taste2plate.app.data.callbacks

import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.Store

class StoreCallback {

    @SerializedName("store")
    lateinit var store: Store
}
