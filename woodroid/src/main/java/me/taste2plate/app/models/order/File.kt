package me.taste2plate.app.models.order


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class File(
    @SerializedName("acl")
    val acl: String,
    @SerializedName("bucket")
    val bucket: String,
    @SerializedName("contentDisposition")
    val contentDisposition: Any,
    @SerializedName("contentType")
    val contentType: String,
    @SerializedName("encoding")
    val encoding: String,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("fieldname")
    val fieldname: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("metadata")
    val metadata: Any,
    @SerializedName("mimetype")
    val mimetype: String,
    @SerializedName("originalname")
    val originalname: String,
    @SerializedName("serverSideEncryption")
    val serverSideEncryption: Any,
    @SerializedName("size")
    val size: Int,
    @SerializedName("storageClass")
    val storageClass: String
):Serializable