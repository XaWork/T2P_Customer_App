package me.taste2plate.app.customer.models;

import java.io.Serializable;

import me.taste2plate.app.models.Product;
import me.taste2plate.app.models.newproducts.NewProduct;


public class CartLineItem extends Model implements Serializable {

    public float price;
    public int quantity;
    public String productId;
    public int shippingCost;

    NewProduct product;
    String name;
    String imageUrl;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setProduct(NewProduct product) {
        this.product = product;
    }

    public NewProduct getProduct() {
        return product;
    }

    public void setShippingCost(int shippingCost) {
        this.shippingCost = shippingCost;
    }

    public int getShippingCost() {
        return shippingCost;
    }
}
