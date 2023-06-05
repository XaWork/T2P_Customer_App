package me.taste2plate.app.models


import com.google.gson.annotations.SerializedName

data class SubCategories(
    val message: String,
    val result: List<Result>,
    val status: String // success
) {
    data class Result(
        val active: Int, // 1
        @SerializedName("created_date")
        val createdDate: String, // 2021-04-08T09:04:02.980Z
        val deleted: Int, // 0
        val description: String, // All the sweets made out of other than using Chena or Mawa
        @SerializedName("description_after_content")
        val descriptionAfterContent: String, // test
        val `file`: String, // https://t2p.fra1.digitaloceanspaces.com/16178735639324kwkw.jpeg
        @SerializedName("_id")
        val id: String, // 606eca9c19796600085c1660
        val name: String, // Sweet - others
        val parent: Parent,
        val slug: String, // sweet-others
        @SerializedName("slug_history")
        val slugHistory: List<String>,
        @SerializedName("update_date")
        val updateDate: String, // 2021-06-19T02:55:36.000Z
        @SerializedName("__v")
        val v: Int // 0
    ) {
        data class Parent(
            @SerializedName("_id")
            val id: String, // 6050bf608f37b70008b0ed38
            val name: String // SWEET
        )
    }
}