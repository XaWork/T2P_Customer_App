package me.taste2plate.app.models.home


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class HiddenGems(
    val active: Int,
    @SerializedName("created_date")
    val createdDate: String,
    val deleted: Int,
    val desc: String,
    val `file`: String,
    val gem: Int,
    @SerializedName("_id")
    val id: String,
    val name: String,
    @SerializedName("seo_description")
    val seoDescription: String,
    @SerializedName("seo_keywords")
    val seoKeywords: String,
    @SerializedName("seo_title")
    val seoTitle: String,
    @SerializedName("short_desc")
    val shortDesc: String,
    val slug: String,
    @SerializedName("slug_history")
    val slugHistory: List<String>,
    val top: Int,
    @SerializedName("update_date")
    val updateDate: String,
    @SerializedName("__v")
    val v: Int
)