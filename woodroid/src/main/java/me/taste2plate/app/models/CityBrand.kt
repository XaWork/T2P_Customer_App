package me.taste2plate.app.models

import androidx.annotation.Keep
import java.io.Serializable

@Keep
class CityBrand : Serializable {

    var result: List<Result>? = null

    @Keep
    class Result: Serializable {
        var _id: String = ""
        var name: String? = null
        var description: String? = null
        var desc: String? = null
        var file = ""
    }
}
