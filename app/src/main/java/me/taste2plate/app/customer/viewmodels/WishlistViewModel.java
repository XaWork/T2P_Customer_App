package me.taste2plate.app.customer.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;

import java.util.Map;

import javax.inject.Inject;

import me.taste2plate.app.customer.common.CompletionGenericLiveData;
import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.customer.models.CartLineItem;
import me.taste2plate.app.customer.repo.CartRepository;
import me.taste2plate.app.customer.repo.CustomerRepository;
import me.taste2plate.app.customer.repo.OrderRepository;
import me.taste2plate.app.customer.repo.ProductRepository;
import me.taste2plate.app.models.CommonResponse;
import me.taste2plate.app.models.LineItem;
import me.taste2plate.app.models.cart.CartItemResponse;


public final class WishlistViewModel extends ViewModel {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Inject
    WishlistViewModel(CartRepository cartRepository, OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.cartRepository =  cartRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public CompletionGenericLiveData<DocumentReference> addToCart(CartLineItem cartItem) {
        return cartRepository.addToCart(cartItem);
    }

    public WooLiveData<CartItemResponse> cart(String userId, String cityId, String zipCode) {
        return productRepository.cartProducts(userId, cityId, zipCode);
    }


    public WooLiveData<CommonResponse> addToCart(int quantity, String pId, String userId) {
        return productRepository.addToCart(userId, pId, quantity);
    }

    public WooLiveData<CommonResponse> updateCart(int quantity, String pId, String userId) {
        return productRepository.updateCart(userId, pId, quantity);
    }

    public WooLiveData<CommonResponse> deleteItem(String productId, String userId) {
        return productRepository.deleteItem(productId, userId);
    }

    public WooLiveData<Map<String, LineItem>> cart(Context context) {
        return cartRepository.cart(context);
    }

}