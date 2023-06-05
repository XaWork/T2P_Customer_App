package me.taste2plate.app.models

import java.io.Serializable
import java.util.Date


class ProductReview : Serializable {
    var name: String? = null
    var product_id: String = ""
    var review: String? = null
    var rating: Float = 0.5f
}
