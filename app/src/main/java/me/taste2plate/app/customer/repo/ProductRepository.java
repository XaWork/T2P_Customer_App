package me.taste2plate.app.customer.repo;


import org.jetbrains.annotations.NotNull;

import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.Woocommerce;
import me.taste2plate.app.models.CommonResponse;
import me.taste2plate.app.models.ProductReview;
import me.taste2plate.app.models.address.AddressResponse;
import me.taste2plate.app.models.address.checkout.CouponResponse;
import me.taste2plate.app.models.cart.CartItemResponse;
import me.taste2plate.app.models.checkout.CheckoutResponse;
import me.taste2plate.app.models.filters.ProductReviewFilter;
import me.taste2plate.app.models.home.SliderProductsResponse;
import me.taste2plate.app.models.membership.PlanResponse;
import me.taste2plate.app.models.membership.myplan.MyPlanResponseWithPoints;
import me.taste2plate.app.models.newproducts.NewProductResponse;
import me.taste2plate.app.models.offers.OfferResponse;
import me.taste2plate.app.models.order.confirmorder.OrderConfirmationResponse;
import me.taste2plate.app.models.wallet.TransactionResponse;
import me.taste2plate.app.models.wishlist.DeleteWishlistItemResponse;
import me.taste2plate.app.models.wishlist.WishlistItemResponse;
import retrofit2.Call;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class ProductRepository {

    @Inject
    Woocommerce woocommerce;

    @Inject
    public ProductRepository() {
    }

    public WooLiveData<NewProductResponse> productByFilter(Map<String, String> filters, String taste) {
        final WooLiveData<NewProductResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().productByFilter(filters, taste).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<NewProductResponse> productsBySubcategory(String id, String taste) {
        final WooLiveData<NewProductResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().productsBySubcategory(id, taste).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<NewProductResponse> productsByCity(String id, String taste) {
        final WooLiveData<NewProductResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().productsByCity(id, taste).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<NewProductResponse> productsByBrand(String id, String taste) {
        final WooLiveData<NewProductResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().productsByBrand(id, taste).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<SliderProductsResponse> productsBySlider(String name, String taste) {
        final WooLiveData<SliderProductsResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().productsBySlider(name, taste).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<NewProductResponse> productsByCuisine(String id, String taste) {
        final WooLiveData<NewProductResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().productsByCuisine(id, taste).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<NewProductResponse> productByQuery(String query) {
        final WooLiveData<NewProductResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().productByQuery(query).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<CartItemResponse> cartProducts(String userId, String cityId, String zipCode) {
        final WooLiveData<CartItemResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().cartProducts(userId, cityId, zipCode).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<OrderConfirmationResponse> confirmOrder(Boolean isWalletApplied, String gateWay, String orderId, String transactionId) {
        final WooLiveData<OrderConfirmationResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().confirmOrder(isWalletApplied, gateWay, orderId, transactionId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<CouponResponse> applyOffers(String userId, String coupon, String cityId, String customerZip) {
        final WooLiveData<CouponResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().applyOffers(userId, coupon, cityId, customerZip).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<CommonResponse> addToCart(String userId, String pId, int quantity) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().addToCart(userId, quantity, pId).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<CommonResponse> addToWishlist(String userId, String productId) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().addToWishlist(userId, productId).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<WishlistItemResponse> getWishlist(String userId) {
        final WooLiveData<WishlistItemResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().getWishlist(userId).enqueue(callBack);
        return callBack;
    }



    public WooLiveData<DeleteWishlistItemResponse> deleteFromWishlist(String userId, String productId) {
        final WooLiveData<DeleteWishlistItemResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().deleteFromWishlist(userId, productId).enqueue(callBack);
        return callBack;
    }





    public WooLiveData<AddressResponse> getUserAddress(String userId) {
        final WooLiveData<AddressResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().getUserAddress(userId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<OfferResponse> getOffers(String cityId) {
        final WooLiveData<OfferResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().getOffers(cityId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<CommonResponse> updateCart(String userId, String pId, int quantity) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().updateCart(userId, quantity, pId).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<CommonResponse> deleteItem(String productId, String userId) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().deleteItem(productId, userId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<NewProductResponse> productById(String productId) {
        final WooLiveData<NewProductResponse> callBack = new WooLiveData();

        woocommerce.ProductRepository().productById(productId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<List<ProductReview>> reviews(int productId) {
        final WooLiveData<List<ProductReview>> callBack = new WooLiveData();

        ProductReviewFilter filter = new ProductReviewFilter();
        int[] products = {productId};

        filter.setProduct(products);

        woocommerce.ReviewRepository().reviews(filter).enqueue(callBack);
        return callBack;
    }


    @NotNull
    public WooLiveData<CheckoutResponse> initCheckout(@NotNull Boolean isWalletApplied,@NotNull String userId, @NotNull String addressId, @NotNull String timeSlot, @NotNull String date, @NotNull String deliveryCost, @NotNull String express, @NotNull String couponCode, @NotNull String couponType, @NotNull String couponAmount, String tipPrice,String cartPrice, String finalPrice, @NotNull String customerCity, String addCost, @NotNull String cutomerZip, @NotNull String browser) {
        final WooLiveData<CheckoutResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().initCheckout(isWalletApplied,userId,addressId,timeSlot,date,deliveryCost,express,couponCode,couponType,couponAmount,cartPrice,finalPrice,tipPrice,customerCity,addCost, cutomerZip, browser).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<CommonResponse> saveReview(String userId, String productId, String name, String email, String mobile, Float rating, String reviewText) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().saveReview(userId,productId,name,email,mobile, rating, reviewText).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<PlanResponse> getPlans(String cityId) {
        final WooLiveData<PlanResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().getPlans(cityId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<CommonResponse> assignPlan(String planId, String userId) {
        final WooLiveData<CommonResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().assignPlan(planId, userId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<MyPlanResponseWithPoints> getMyPlan(String userId) {
        final WooLiveData<MyPlanResponseWithPoints> callBack = new WooLiveData();
        woocommerce.ProductRepository().getMyPlan(userId).enqueue(callBack);
        return callBack;
    }

    public WooLiveData<TransactionResponse> getWalletTransactions(String userId) {
        final WooLiveData<TransactionResponse> callBack = new WooLiveData();
        woocommerce.ProductRepository().getWalletTransactions(userId).enqueue(callBack);
        return callBack;
    }
}
