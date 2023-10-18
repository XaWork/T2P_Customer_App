package me.taste2plate.app.data.api


import me.taste2plate.app.models.CommonResponse
import me.taste2plate.app.models.Product
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
import retrofit2.http.*

interface ProductAPI {


    @FormUrlEncoded
    @POST("app/checkout")
    fun initCheckout(
        @Field("wallet_discount") walletDiscount: Boolean,
        @Field("userid") userId: String,
        @Field("address") addressId: String,
        @Field("timeslot") timeSlot: String,
        @Field("delivery_date") date: String,
        @Field("shipping_price") deliveryCost: String,
        @Field("express") express: String,
        @Field("coupon") couponCode: String,
        @Field("coupontype") couponType: String,
        @Field("couponamount") couponAmount: String,
        @Field("price") cartPrice: String,
        @Field("tip_price") tipPrice: String,
        @Field("final_price") finalPrice: String,
        @Field("customer_city") customerCity: String,
        @Field("additional_cost") addCost: String,
        @Field("customer_zip") zip: String,
        @Field("browser") browser: String
    ): Call<CheckoutResponse>


    @GET("app/product-details")
    fun productById(@Query("id") id: String): Call<NewProductResponse>


    @GET("app/all-products")
    fun productBySubcategory(
        @Query("sub_category") id: String,
        @Query("taste") taste: String
    ): Call<NewProductResponse>

    @GET("app/all-products")
    fun productByFilters(
        @QueryMap filter: Map<String, String>,
        @Query("taste") taste: String
    ): Call<NewProductResponse>

    @GET("app/all-products")
    fun productsByCity(
        @Query("city") id: String,
        @Query("taste") taste: String
    ): Call<NewProductResponse>

    @GET("app/all-products")
    fun productsByBrand(
        @Query("brand") id: String,
        @Query("taste") taste: String
    ): Call<NewProductResponse>

    @POST("app/fetch-shop-by-slider")
    fun productsBySlider(
        @Query("slider_name") name: String,
        @Query("taste") taste: String
    ): Call<SliderProductsResponse>

    @GET("app/all-products")
    fun productsByCuisine(
        @Query("cuisine") id: String,
        @Query("taste") taste: String
    ): Call<NewProductResponse>

    @GET("app/all-products")
    fun productByQuery(@Query("search") searchQuert: String): Call<NewProductResponse>

    @GET("app/get-cart")
    fun cartProducts(
        @Query("id") id: String,
        @Query("customer_city") cityId: String,
        @Query("customer_zip") zipCode: String
    ): Call<CartItemResponse>


    @FormUrlEncoded
    @POST("app/checkout-confirm")
    fun confirmOrder(
        @Field("wallet_discount") isWalletApplied: Boolean,
        @Field("gateway") gateWay: String,
        @Field("orderid") orderId: String,
        @Field("transactionid") transactionId: String
    ): Call<OrderConfirmationResponse>

    @FormUrlEncoded
    @POST("app/apply-coupon")
    fun applyOffers(
        @Field("coupon") coupon: String,
        @Field("userid") userid: String,
        @Field("customer_city") cityId: String,
        @Field("1") zipCode: String
    ): Call<CouponResponse>

    @FormUrlEncoded
    @POST("app/add-to-cart")
    fun addToCart(
        @Field("id") userId: String,
        @Field("productid") pId: String,
        @Field("quantity") quantity: Int
    ): Call<CommonResponse>


    @FormUrlEncoded
    @POST("app/add-to-wish")
    fun addToWishlist(
        @Field("id") userId: String,
        @Field("productid") productId: String
    ): Call<CommonResponse>


    @GET("app/get-wish")
    fun getWishlist(
        @Query("id") userId: String
    ): Call<WishlistItemResponse>

    @GET("app/delete-wish")
    fun deleteFromWishlist(
        @Query("userid") userId: String,
        @Query("id") productId: String
    ): Call<DeleteWishlistItemResponse>


    @GET("app/all-address")
    fun getUserAddress(@Query("id") userId: String): Call<AddressResponse>


    @GET("app/offer-deal")
    fun getOfferByCity(@Query("city") cityId: String): Call<OfferResponse>

    @FormUrlEncoded
    @POST("app/update-cart")
    fun updateCart(
        @Field("userid") userId: String,
        @Field("id") pId: String,
        @Field("quantity") quantity: Int
    ): Call<CommonResponse>

    @GET("app/delete-cart")
    fun deleteItem(
        @Query("userid") userId: String,
        @Query("id") productId: String
    ): Call<CommonResponse>

    fun batch(@Body body: Product): Call<String>

    @FormUrlEncoded
    @POST("app/add-review")
    fun postReview(
        @Field("user") userId: String,
        @Field("product") productId: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("rating") rating: Float,
        @Field("review") reviewText: String
    ): Call<CommonResponse>

    @GET("app/all-plan-list")
    fun getPlans(@Query("city") cityId: String): Call<PlanResponse>

    @FormUrlEncoded
    @POST("app/assign-plan")
    fun assignPlan(@Field("plan") planId: String, @Field("id") userId: String): Call<CommonResponse>


    @GET("app/get-wallet-data")
    fun getMyPlan(@Query("id") userId: String): Call<MyPlanResponseWithPoints>

    @POST("app/wallet-transactions")
    fun getWalletTransactions(@Query("id") userId: String): Call<TransactionResponse>
}