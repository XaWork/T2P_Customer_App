package me.taste2plate.app.models

import java.io.Serializable

class Category : Serializable {
    val result:List<Result>? = null

    class Result : Serializable{
        var _id: String = ""
        var name: String? = null
        var parent: String? = null
        var description: String = ""
        //var image: Image? = null
        var file: String = ""
        var count: Int = 0
    }
}
