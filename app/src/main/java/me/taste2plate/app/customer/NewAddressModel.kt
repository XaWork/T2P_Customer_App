package me.taste2plate.app.customer


import com.google.gson.annotations.SerializedName

data class NewAddressModel(
    val message: String,
    val result: List<Result>,
    val status: String // success
) {
    data class Result(
        val address: String, // Abc new, abc
        val address2: String, // Abc
        val city: City,
        @SerializedName("contact_mobile")
        val contactMobile: String, // 8010265036
        @SerializedName("contact_name")
        val contactName: String, // do not ship
        @SerializedName("created_date")
        val createdDate: String, // 2022-03-12T08:41:43.558Z
        val deleted: Int, // 0
        @SerializedName("_id")
        val id: String, // 622c5cc7567ad55711d9ad7f
        val landmark: String?, // land mark 2
        val pincode: String, // 110022
        val position: Position,
        @SerializedName("post_office")
        val postOffice: String?, // N/A
        val state: State,
        val title: String, // home
        @SerializedName("update_date")
        val updateDate: String, // 2022-03-09T16:46:59.481Z
        val user: String, // 6083ea4be2c8890008fbb05a
        @SerializedName("__v")
        val v: Int // 0
    ) {
        data class City(
            @SerializedName("_id")
            val id: String, // 6040eb2b4a4b6c0008fe1b01
            val name: String // DELHI (NCR)- Delivery
        )

        data class Position(
            val coordinates: List<Double>,
            val type: String // Point
        )

        data class State(
            @SerializedName("_id")
            val id: String, // 5f665ee12d45902c98aa8f06
            val name: String // Delhi (DL)
        )
    }
}