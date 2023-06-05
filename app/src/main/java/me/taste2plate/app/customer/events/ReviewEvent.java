package me.taste2plate.app.customer.events;

import me.taste2plate.app.models.ProductReview;

public class ReviewEvent {

    ProductReview review;

    public ReviewEvent(ProductReview review) {
        this.review = review;
    }

    public ProductReview getReview() {
        return review;
    }

    public void setReview(ProductReview review) {
        this.review = review;
    }
}
