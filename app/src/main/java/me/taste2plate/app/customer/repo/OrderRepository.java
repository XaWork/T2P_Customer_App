package me.taste2plate.app.customer.repo;


import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.Woocommerce;
import me.taste2plate.app.models.Order;
import me.taste2plate.app.models.filters.OrderFilter;
import me.taste2plate.app.models.order.MyOrderResponse;

import javax.inject.Inject;
import java.util.List;

public class OrderRepository {

    @Inject
    Woocommerce woocommerce;


    @Inject
    public OrderRepository() {

    }

    public WooLiveData<Order> addToCart(int productId) {
        final WooLiveData<Order> callBack = new WooLiveData();

        //woocommerce.OrderRepository().addToCart(productId, null).enqueue(callBack);
        return callBack;
    }


    public WooLiveData<MyOrderResponse> orders(String userId) {
        final WooLiveData<MyOrderResponse> callBack = new WooLiveData();
        woocommerce.OrderRepository().orders(userId).enqueue(callBack);
        return callBack;
    }

}
