package me.taste2plate.app.models.wallet

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TransactionResponse(
    @SerializedName("result")
    val result: List<Transaction>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalCount")
    val totalCount: Int
)