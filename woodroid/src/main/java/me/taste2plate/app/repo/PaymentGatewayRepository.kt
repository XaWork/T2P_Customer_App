package me.taste2plate.app.repo

import retrofit2.Call

import me.taste2plate.app.data.api.PaymentGatewayAPI
import me.taste2plate.app.models.PaymentGateway

class PaymentGatewayRepository(baseUrl: String, consumerKey: String, consumerSecret: String) :
    WooRepository(baseUrl, consumerKey, consumerSecret) {

    private val apiService: PaymentGatewayAPI

    init {
        apiService = retrofit.create(PaymentGatewayAPI::class.java)
    }

    fun paymentGateway(id: Int): Call<PaymentGateway> {
        return apiService.view(id)
    }

    fun paymentGateways(): Call<List<PaymentGateway>> {
        return apiService.list()
    }

    fun update(id: String, paymentGateway: PaymentGateway): Call<PaymentGateway> {
        return apiService.update(id, paymentGateway)
    }

}
