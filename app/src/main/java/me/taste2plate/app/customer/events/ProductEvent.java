package me.taste2plate.app.customer.events;

import me.taste2plate.app.models.Product;

public class ProductEvent {

    Product product;

    public ProductEvent(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
