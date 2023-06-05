package me.taste2plate.app.customer.events;

import me.taste2plate.app.customer.models.CartLineItem;
import me.taste2plate.app.models.cart.CartItem;

public class LessQuantityEvent {

    CartItem cartLineItem;

    public LessQuantityEvent(CartItem cartLineItem) {
        this.cartLineItem = cartLineItem;
    }

    public CartItem getCartLineItem() {
        return cartLineItem;
    }

    public void setCartLineItem(CartItem cartLineItem) {
        this.cartLineItem = cartLineItem;
    }
}
