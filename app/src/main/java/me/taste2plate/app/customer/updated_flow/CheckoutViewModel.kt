package me.taste2plate.app.customer.updated_flow

import androidx.lifecycle.ViewModel
import me.taste2plate.app.customer.common.WooLiveData
import me.taste2plate.app.customer.repo.CustomRepository
import me.taste2plate.app.customer.repo.ProductRepository
import me.taste2plate.app.models.address.AddressResponse
import me.taste2plate.app.models.address.checkout.CouponResponse
import me.taste2plate.app.models.cart.CartItemResponse
import me.taste2plate.app.models.checkout.CheckoutResponse
import me.taste2plate.app.models.cutoff.CutOffResponse
import me.taste2plate.app.models.offers.OfferResponse
import me.taste2plate.app.models.order.confirmorder.OrderConfirmationResponse
import javax.inject.Inject

class CheckoutViewModel @Inject constructor(private val productRepository: ProductRepository, private val customRepository: CustomRepository) : ViewModel() {

    fun cart(userId: String, cityId:String, zipCode:String): WooLiveData<CartItemResponse> {
        return productRepository.cartProducts(userId, cityId, zipCode)
    }

    fun confirmOrder(isWalletApplied:Boolean,gateWay: String, orderId:String, transactionId:String): WooLiveData<OrderConfirmationResponse> {
        return productRepository.confirmOrder(isWalletApplied, gateWay, orderId, transactionId)
    }

    fun checkCutOfftime(
        startingCity: String,
        endCity: String
    ): WooLiveData<CutOffResponse?>{
        return customRepository.checkCutOfftime(startingCity, endCity)
    }

    fun applyOffers(couponCode: String, userId: String, cityId: String, customerZip:String): WooLiveData<CouponResponse> {
        return productRepository.applyOffers(userId, couponCode, cityId,customerZip)
    }

    fun fetchUserAddress(userId: String): WooLiveData<AddressResponse>{
        return productRepository.getUserAddress(userId)
    }

    fun fetchOffers(cityId: String): WooLiveData<OfferResponse>{
        return productRepository.getOffers(cityId)
    }

    fun initCheckout(
        isWalletApplied: Boolean,
        userId: String,
        addressId: String,
        timeSlot: String,
        date: String,
        deliveryCost: String,
        express: String,
        couponCode: String,
        couponType: String,
        couponAmount: String,
        cartPrice: String,
        finalPrice: String,
        customerCity: String,
        addCost: String,
        customerZip: String,
        browser: String
    ):WooLiveData<CheckoutResponse> {
        return productRepository.initCheckout(isWalletApplied,userId,addressId,timeSlot,date,deliveryCost,express,couponCode,couponType,couponAmount,cartPrice,finalPrice,customerCity,addCost,customerZip, browser)
    }

}