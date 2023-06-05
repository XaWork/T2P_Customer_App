package me.taste2plate.app.repo

import me.taste2plate.app.data.api.ProductAPI
import me.taste2plate.app.models.CommonResponse
import me.taste2plate.app.models.address.AddressResponse
import me.taste2plate.app.models.address.checkout.CouponResponse
import me.taste2plate.app.models.cart.CartItemResponse
import me.taste2plate.app.models.checkout.CheckoutResponse
import me.taste2plate.app.models.home.SliderProductsResponse
import me.taste2plate.app.models.membership.PlanResponse
import me.taste2plate.app.models.membership.myplan.MyPlanResponseWithPoints
import me.taste2plate.app.models.newproducts.NewProductResponse
import me.taste2plate.app.models.offers.OfferResponse
import me.taste2plate.app.models.order.confirmorder.OrderConfirmationResponse
import me.taste2plate.app.models.wallet.TransactionResponse
import me.taste2plate.app.models.wishlist.DeleteWishlistItemResponse
import me.taste2plate.app.models.wishlist.WishlistItemResponse
import retrofit2.Call

class ProductRepository(baseUrl: String, consumerKey: String, consumerSecret: String) :
    WooRepository(baseUrl, consumerKey, consumerSecret) {

    private val apiService: ProductAPI

    init {
        apiService = retrofit.create(ProductAPI::class.java)
    }


    fun productById(id: String): Call<NewProductResponse> {
        return apiService.productById(id)
    }

    fun productsBySubcategory(id: String): Call<NewProductResponse> {
        return apiService.productBySubcategory(id)
    }

    fun productByFilter(filters: Map<String, String>): Call<NewProductResponse> {
        return apiService.productByFilters(filters)
    }

    fun productsByCity(id: String): Call<NewProductResponse> {
        return apiService.productsByCity(id)
    }

    fun productsByBrand(id: String): Call<NewProductResponse> {
        return apiService.productsByBrand(id)
    }

    fun productsBySlider(name: String): Call<SliderProductsResponse> {
        return apiService.productsBySlider(name)
    }

    fun productsByCuisine(id: String): Call<NewProductResponse> {
        return apiService.productsByCuisine(id)
    }

    fun productByQuery(query: String): Call<NewProductResponse> {
        return apiService.productByQuery(query)
    }

    fun cartProducts(userId: String, cityId: String, zipCode: String): Call<CartItemResponse> {
        return apiService.cartProducts(userId, cityId, zipCode)
    }

    fun confirmOrder(
        isWalletApplied: Boolean,
        gateWay: String,
        orderId: String,
        transactionId: String
    ): Call<OrderConfirmationResponse> {
        return apiService.confirmOrder(isWalletApplied, gateWay, orderId, transactionId)
    }

    fun applyOffers(
        userId: String,
        couponCode: String,
        cityId: String,
        customerZip: String
    ): Call<CouponResponse> {
        return apiService.applyOffers(couponCode, userId, cityId, customerZip)
    }

    fun addToCart(userId: String, quantity: Int, pId: String): Call<CommonResponse> {
        return apiService.addToCart(userId, pId, quantity)
    }


    fun addToWishlist(userId: String, productId: String): Call<CommonResponse> {
        return apiService.addToWishlist(userId, productId)
    }


    fun getWishlist(userId: String): Call<WishlistItemResponse> {
        return apiService.getWishlist(userId)
    }


    fun deleteFromWishlist(userId: String, productId: String): Call<DeleteWishlistItemResponse> {
        return apiService.deleteFromWishlist(userId, productId)
    }


    fun getUserAddress(userId: String): Call<AddressResponse> {
        return apiService.getUserAddress(userId)
    }


    fun getOffers(cityId: String): Call<OfferResponse> {
        return apiService.getOfferByCity(cityId)
    }

    fun updateCart(userId: String, quantity: Int, pId: String): Call<CommonResponse> {
        return apiService.updateCart(userId, pId, quantity)
    }

    fun deleteItem(productId: String, userId: String): Call<CommonResponse> {
        return apiService.deleteItem(userId, productId)
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
    ): Call<CheckoutResponse> {
        return apiService.initCheckout(
            isWalletApplied,
            userId,
            addressId,
            timeSlot,
            date,
            deliveryCost,
            express,
            couponCode,
            couponType,
            couponAmount,
            cartPrice,
            finalPrice,
            customerCity,
            addCost,
            customerZip,
            browser
        )
    }

    fun saveReview(
        userId: String,
        productId: String,
        name: String,
        email: String,
        mobile: String,
        rating: Float,
        reviewText: String
    ): Call<CommonResponse> {
        return apiService.postReview(userId, productId, name, email, mobile, rating, reviewText);
    }

    fun getPlans(cityId: String): Call<PlanResponse> {
        return apiService.getPlans(cityId)
    }

    fun assignPlan(planId: String, userId: String): Call<CommonResponse> {
        return apiService.assignPlan(planId, userId)
    }

    fun getMyPlan(userId: String): Call<MyPlanResponseWithPoints> {
        return apiService.getMyPlan(userId)
    }

    fun getWalletTransactions(userId: String): Call<TransactionResponse> {
        return apiService.getWalletTransactions(userId)
    }
}
