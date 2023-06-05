package me.taste2plate.app.models.address

data class AddressResponse(
    val message: String,
    val result: List<Address>,
    val status: String
)