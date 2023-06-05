package me.taste2plate.app.models

import java.io.Serializable
import java.util.*


class Product : Serializable {

    var id: Int = 0
    var post_id: Int = 0
    var name: String = ""
    var slug: String = ""
    var type: String = ""
    var status: String = ""
    var description: String = ""
    var price: String = ""
    var regular_price: String = ""
    var sale_price: String = ""
    var price_html: String = ""
    var isOn_sale: Boolean = false
    var shipping_class_id: Int = 0
    var related_ids = ArrayList<Int>()
    var categories = ArrayList<Category>()
    var images = ArrayList<Image>()
    var img = ArrayList<String>()
    var image :String=""
    var title :String=""

}


