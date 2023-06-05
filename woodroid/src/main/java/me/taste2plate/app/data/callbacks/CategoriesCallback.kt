package me.taste2plate.app.data.callbacks


import com.google.gson.annotations.SerializedName
import me.taste2plate.app.models.Category


import java.util.ArrayList


class CategoriesCallback {
    @SerializedName("product_categories")
    lateinit var categories: ArrayList<Category>
}
