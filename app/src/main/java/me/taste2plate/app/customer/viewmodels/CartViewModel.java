package me.taste2plate.app.customer.viewmodels;

import androidx.lifecycle.ViewModel;
import android.content.Context;
import com.google.firebase.firestore.DocumentReference;
import me.taste2plate.app.customer.common.CompletionGenericLiveData;
import me.taste2plate.app.customer.common.QueryLiveData;
import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.customer.models.CartLineItem;
import me.taste2plate.app.customer.repo.CartRepository;
import me.taste2plate.app.customer.repo.CustomerRepository;
import me.taste2plate.app.customer.repo.OrderRepository;
import me.taste2plate.app.customer.repo.ProductRepository;
import me.taste2plate.app.models.*;
import me.taste2plate.app.models.cart.CartItemResponse;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;


public final class CartViewModel extends ViewModel {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Inject
    CartViewModel(CartRepository cartRepository, OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
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