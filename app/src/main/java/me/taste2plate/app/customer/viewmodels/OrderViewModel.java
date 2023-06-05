package me.taste2plate.app.customer.viewmodels;

import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.customer.repo.CustomRepository;
import me.taste2plate.app.customer.repo.OrderRepository;
import me.taste2plate.app.customer.repo.ProductRepository;
import me.taste2plate.app.models.CommonResponse;
import me.taste2plate.app.models.Order;
import me.taste2plate.app.models.Product;
import me.taste2plate.app.models.ShippingTaxResponse;
import me.taste2plate.app.models.filters.OrderFilter;
import me.taste2plate.app.models.filters.ProductFilter;
import me.taste2plate.app.models.order.MyOrderResponse;
import me.taste2plate.app.models.order.updates.OrderUpdateResponse;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.List;


public final class OrderViewModel extends ViewModel {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomRepository customRepository;

    @Inject
    OrderViewModel(OrderRepository orderRepository, ProductRepository productRepository,CustomRepository customRepositor) {
        this.orderRepository =  orderRepository;
        this.productRepository = productRepository;
        this.customRepository = customRepositor;
    }

    public WooLiveData<Order> addToCart(int productId) {
        return orderRepository.addToCart(productId);
    }

    public WooLiveData<MyOrderResponse> orders(String id) {
        return orderRepository.orders(id);
    }

    public WooLiveData<CommonResponse> cancelOrder(String orderId){
        return customRepository.cancelOrder(orderId);
    }

    public WooLiveData<OrderUpdateResponse> getOrderUpdates(String orderId){
        return customRepository.getOrderUpdates(orderId);
    }

}