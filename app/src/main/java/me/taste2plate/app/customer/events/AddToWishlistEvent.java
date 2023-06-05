package me.taste2plate.app.customer.events;


public class AddToWishlistEvent {

    String productId;

    public AddToWishlistEvent(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}


