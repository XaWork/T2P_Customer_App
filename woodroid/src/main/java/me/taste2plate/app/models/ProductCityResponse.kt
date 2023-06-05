package me.taste2plate.app.models

import java.util.ArrayList

data class ProductCityResponse(
   /* val city_id:Int=0*/
    var code: Int = 0,
    var message: String = "",
    var data: ArrayList<ProductCity>
)

data class ProductCity(
    val city_id:Int=0
)