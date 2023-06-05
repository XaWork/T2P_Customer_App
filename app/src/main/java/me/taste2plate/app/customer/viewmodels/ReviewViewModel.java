package me.taste2plate.app.customer.viewmodels;

import androidx.lifecycle.ViewModel;
import me.taste2plate.app.customer.common.WooLiveData;
import me.taste2plate.app.customer.repo.ProductRepository;
import me.taste2plate.app.customer.repo.ReviewRepository;
import me.taste2plate.app.models.CommonResponse;
import me.taste2plate.app.models.ProductReview;
import me.taste2plate.app.models.newproducts.NewProductResponse;

import javax.inject.Inject;
import java.util.List;


public final class ReviewViewModel extends ViewModel {
    private final ProductRepository productRepository;

    @Inject
    ReviewViewModel(ProductRepository productRepository) {
        this.productRepository =  productRepository;
    }

    public WooLiveData<NewProductResponse> productById(String productId) {
        return productRepository.productById(productId);
    }
    public WooLiveData<CommonResponse> saveReview(String userId,
                                                  String productId,
                                                  String reviewText,
                                                  String name,
                                                  String email,
                                                  String mobile,
                                                  Float rating) {
        return productRepository.saveReview(userId,productId,name,email,mobile, rating, reviewText);
    }

}