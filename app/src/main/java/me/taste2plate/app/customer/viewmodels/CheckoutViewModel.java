package me.taste2plate.app.customer.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.google.gson.JsonElement;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.taste2plate.app.customer.common.CompletionGenericLiveData;
import me.taste2plate.app.customer.common.QueryLiveData;
import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.customer.models.CartLineItem;
import me.taste2plate.app.customer.repo.CheckoutRepository;
import me.taste2plate.app.customer.repo.CustomRepository;
import me.taste2plate.app.customer.repo.CustomerRepository;
import me.taste2plate.app.customer.repo.OrderRepository;
import me.taste2plate.app.models.AddressListResponse;
import me.taste2plate.app.models.CommonResponse;
import me.taste2plate.app.models.Customer;
import me.taste2plate.app.models.LineItem;
import me.taste2plate.app.models.Order;
import me.taste2plate.app.models.OrderData;
import me.taste2plate.app.models.ShippingMethods;
import me.taste2plate.app.models.ShippingTaxResponse;
import me.taste2plate.app.models.ValidateCouponRequest;
import me.taste2plate.app.models.address.AddressResponse;
import me.taste2plate.app.models.ordervalidation.OrderValidation;


public final class CheckoutViewModel extends ViewModel {

    private final CheckoutRepository checkoutRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CustomRepository customRepository;

    @Inject
    CheckoutViewModel(CheckoutRepository checkoutRepository, OrderRepository orderRepository, CustomerRepository customerRepository,CustomRepository customRepositor) {
        this.checkoutRepository = checkoutRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.customRepository = customRepositor;
    }

    public QueryLiveData<CartLineItem> cart() {
        return checkoutRepository.cart();
    }

    public CompletionGenericLiveData<Void> deleteAllCartItems() {
        return checkoutRepository.deleteItems();
    }

    public WooLiveData<Map<String, LineItem>> cart(Context context) {
        return checkoutRepository.cart(context);
    }

    public WooLiveData<JsonElement> shippingCost(int shippingId) {
        return checkoutRepository.shippingCost(shippingId);
    }

    public WooLiveData<AddressResponse> getAddressList(String userId) {
        return customRepository.getUserAddress(userId);
    }

}